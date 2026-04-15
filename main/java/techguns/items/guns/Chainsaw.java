package techguns.items.guns;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.client.ClientProxy;
import techguns.client.audio.TGSoundCategory;
import techguns.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.entities.projectiles.InvisibleProjectile;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.packets.PacketPlaySound;
import techguns.util.EntityDeathUtils.DeathType;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class Chainsaw extends GenericGun {
	
	final float collisionSize = 4.0f;
	
	float meleeDamagePwr = 6.0f;
	float meleeDamageEmpty = 2.0f;
	
	public static final float CHAINSAW_SOUND_DISTANCE=2.0f;
	
	public Chainsaw(String name, ItemStack ammo, boolean semiAuto, int minFiretime, int clipsize, int reloadtime, float damage, String firesound,
			String reloadsound, int TTL, float accuracy) {
		super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL, accuracy);
	}
	
	public Chainsaw setMeleeDmg(float dmgFull, float dmgEmpty){
		this.meleeDamagePwr = dmgFull;
		this.meleeDamageEmpty = dmgEmpty;
		return this;
	}

/*	@Override
	public void startCharge(ItemStack item, World world, EntityPlayer player) {
		world.playSoundAtEntity(player, "techguns:guns.powerhammerCharge", 1.0F, 1.0F );
		//player.playSound("techguns:guns.powerhammerCharge", 1.0F, 1.0F );
	}*/
	
	
	
	
	@Override
	protected TGDamageSource getMeleeDamageSource(EntityPlayer player,ItemStack stack) {
		TGDamageSource src = new TGDamageSource("player", player, player, DamageType.PHYSICAL, DeathType.GORE);
		if(this.getCurrentAmmo(stack)>0){
			src.goreChance=1.0f;
			src.armorPenetration=0.25f;
			src.knockbackMultiplier=0.0f;
		} else{
			src.deathType = DeathType.DEFAULT;
		}
		return src;
	}

	
	
	@Override
	protected void spawnProjectile(World world, EntityLivingBase player, ItemStack itemstack, float spread, float offset, float damagebonus, boolean leftGun) {
			//(float) (damage + 2*damage*Math.pow(charge, 2.0f))
			world.spawnEntityInWorld(new InvisibleProjectile(world, player, damage*damagebonus, speed, this.ticksToLive, spread, this.damageDropStart, this.damageDropEnd, this.damageMin*damagebonus, this.penetration, getDoBlockDamage(player)));
	}

	/*@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		if(this.getCurrentAmmo(stack)>0){
			//SoundUtil.playSoundOnEntityGunPosition(entityLiving.worldObj, entityLiving, "techguns:guns.chainsawSwing", SOUND_DISTANCE, 1.0F, false, true);		
			if (entityLiving.worldObj.isRemote) {
				ClientProxy cp = ClientProxy.get();
				if(cp.getplayerRecoiltime(entityLiving)<System.currentTimeMillis()){
					cp.setplayerRecoiltime(entityLiving, System.currentTimeMillis()+500, 500, (byte)1); //TODO: Fix attack Types
					//cp.playSoundOnEntity(entityLiving, "techguns:guns.chainsawSwing", 1.25f, 1.0f, false, false, true);
					SoundUtil.playSoundOnEntityGunPosition(entityLiving.worldObj, entityLiving, "techguns:guns.chainsawSwing", SOUND_DISTANCE, 1.0F, false, true);		
					
				}
				return true;
			}
		}
		return false;
	}*/

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		
		boolean openingContainer=false;
		if (!entityLiving.worldObj.isRemote) {
			if(entityLiving instanceof EntityPlayer){
				EntityPlayer ply = (EntityPlayer) entityLiving;
				if(ply.openContainer!=null){
					if (!(ply.openContainer.getClass() == ContainerPlayer.class)){
						openingContainer=true;
					}
				}
				//System.out.println("Container:"+ply.openContainer);
			}
		}
		
		if ( this.getCurrentAmmo(stack) > 0) {
			// SoundUtil.playSoundOnEntityGunPosition(entityLiving.worldObj,
			// entityLiving, "techguns:guns.powerhammerSwing", SOUND_DISTANCE,
			// 1.0F, false, false);
			if (entityLiving.worldObj.isRemote) {
				ClientProxy cp = ClientProxy.get();
				if (cp.getplayerRecoiltime(entityLiving) < System.currentTimeMillis()) {
					cp.setplayerRecoiltime(entityLiving, System.currentTimeMillis() + 250, 250, (byte) 0);
					// cp.playSoundOnEntity(entityLiving,
					// "techguns:guns.powerhammerSwing", 0.75f, 1.0f, false,
					// false, true);
				}
				return true;
			} else {
				
				boolean sendSound=true;
				if(entityLiving instanceof EntityPlayer){
					TechgunsExtendedPlayerProperties props = TechgunsExtendedPlayerProperties.get((EntityPlayer)entityLiving);
					if(props.swingSoundDelay>0){
						sendSound=false;
					} else {
						props.swingSoundDelay=5;
					}
				}
				
				//server side:;
				//SoundUtil.playSoundOnEntityGunPosition(entityLiving.worldObj, entityLiving, "techguns:guns.powerhammerSwing", SOUND_DISTANCE, 1.0F, false, false);	
				if(!openingContainer && sendSound){
					TGPackets.network.sendToAllAround(new PacketPlaySound("techguns:guns.chainsawSwing", entityLiving, CHAINSAW_SOUND_DISTANCE, 1.0f, false, false, true, true, TGSoundCategory.GUN_FIRE), new TargetPoint(entityLiving.worldObj.provider.dimensionId, entityLiving.posX, entityLiving.posY, entityLiving.posZ, 50.0f));
				}
				
				return true;
			}
		}
		return false;
	}
	

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		this.shootGunLeftClick(item, world, player, false);
		return item;
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		double meleedmg=this.meleeDamagePwr;
		int ammoleft = this.getCurrentAmmo(stack);
		if (ammoleft <=0){
			meleedmg=this.meleeDamageEmpty;
		}
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", meleedmg, 0));
		return multimap;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block b, int x, int y, int z,EntityLivingBase ent) {
		if(this.getCurrentAmmo(stack)>0){
			this.useAmmo(stack, 1);
			world.playSoundEffect(x+0.5D, y+0.5D, z+0.5D, "techguns:guns.chainsawHit", 0.65f, 1.0f);
			return true;
		}
		return false;
	}

	@Override
	public DamageType getDamageType() {
		return DamageType.PHYSICAL;
	}
	

}
