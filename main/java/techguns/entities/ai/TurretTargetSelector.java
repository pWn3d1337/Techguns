package techguns.entities.ai;

import java.util.UUID;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import techguns.entities.npc.NPCTurret;
import techguns.entities.npc.TGNpcFactions;

public class TurretTargetSelector implements IEntitySelector {

	protected NPCTurret turret;
	
	public TurretTargetSelector(NPCTurret turret) {
		super();
		this.turret = turret;
	}



	@Override
	public boolean isEntityApplicable(Entity entity) {

		if(entity instanceof IMob){
			return true;
		}
		
		if(turret.mountedTileEnt!=null){
			byte security = turret.mountedTileEnt.getSecurity();
			UUID owner =turret.mountedTileEnt.getOwner();
			
			if (security!= 0 && owner!=null){
				
			//	System.out.println("Checking for Target:"+entity);
				
				if(entity instanceof NPCTurret){
					NPCTurret otherTurret = (NPCTurret) entity;
					if (otherTurret.mountedTileEnt!=null){
						UUID otherOwner = otherTurret.mountedTileEnt.getOwner();
						return TGNpcFactions.isHostile(owner, otherOwner);						
					}
					
					
				} else if ( entity instanceof EntityPlayer){
				//	System.out.println("Checking for Player Target:"+entity);
					UUID ply = ((EntityPlayer)entity).getGameProfile().getId();
					if (ply!=null){
						if (owner.equals(ply)){
							return false;
						}
						
						return TGNpcFactions.isHostile(owner, ply);
						
					}
				} 								
								
			} 
			if ( turret.mountedTileEnt.attackAnimals && entity instanceof IAnimals && ! (entity instanceof NPCTurret)){
				if (entity instanceof EntityTameable){
					if (((EntityTameable)entity).isTamed()){
						return false;
					}
					return true;
					
				} else if (entity instanceof EntityHorse){
					if (((EntityHorse)entity).isTame()){
						return false;
					}
					return true;
					
				} else if (entity instanceof IEntityOwnable){
					if (((IEntityOwnable)entity).getOwner()!=null){
						return false;
					}
					return true;
				}
				
				return true;
			}
		}
		//System.out.println("Turret is null");
		//Fallbacks for no UUID/owner set etc...
		
		/**
		 * no turret vs turret
		 */
		if (entity instanceof NPCTurret){
			return false;
		}

		if(entity instanceof EntityPlayer){
			return false;
		}
		
		return false;
	}

}
