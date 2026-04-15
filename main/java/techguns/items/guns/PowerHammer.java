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
import techguns.entities.projectiles.PowerHammerProjectile;
import techguns.extendedproperties.TechgunsExtendedPlayerProperties;
import techguns.packets.PacketPlaySound;
import techguns.util.EntityDeathUtils.DeathType;
import techguns.util.MathUtil;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class PowerHammer extends GenericGunCharge{
	
	final float collisionSize = 4.0f;
	
	float meleeDamagePwr = 6.0f;
	float meleeDamageEmpty = 2.0f;
	
	static final float POWERHAMMER_SOUNDDISTANCE=1.0f;
	
	public PowerHammer(String name, ItemStack ammo, boolean semiAuto, int minFiretime, int clipsize, int reloadtime, float damage, String firesound,
			String reloadsound) {
		super(name, ammo, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, 2, 0,20.0f);
		this.ammoConsumedOnFullCharge=10;
	}
	
	public PowerHammer setMeleeDmg(float dmgFull, float dmgEmpty){
		this.meleeDamagePwr = dmgFull;
		this.meleeDamageEmpty = dmgEmpty;
		return this;
	}
	
	@Override
	public void spawnChargedProjectile(final World world, final EntityLivingBase player, ItemStack itemStack, float spread, float charge, int ammoConsumed) {
		float dmg = ammoConsumed*damage;
				
		//(float) (damage + 2*damage*Math.pow(charge, 2.0f))
		world.spawnEntityInWorld(new PowerHammerProjectile(world, player, dmg, speed, this.ticksToLive, this.collisionSize, charge,  this.damageDropStart, this.damageDropEnd,
				this.damageMin, this.penetration, getDoBlockDamage(player)));
	}
	
	@Override
	public void startCharge(ItemStack item, World world, EntityPlayer player) {
		world.playSoundAtEntity(player, "techguns:guns.powerhammerCharge", 1.0F, 1.0F );
		//player.playSound("techguns:guns.powerhammerCharge", 1.0F, 1.0F );
	}
	
	@Override
	protected TGDamageSource getMeleeDamageSource(EntityPlayer player,ItemStack stack) {
		TGDamageSource src = new TGDamageSource("player", player, player, DamageType.PHYSICAL, DeathType.GORE);
		if(this.getCurrentAmmo(stack)>0){
			src.goreChance=1.0f;
			src.armorPenetration=0.25f;
			src.knockbackMultiplier=1.5f;
		} else{
			src.deathType = DeathType.DEFAULT;
		}
		return src;
	}

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
					TGPackets.network.sendToAllAround(new PacketPlaySound("techguns:guns.powerhammerSwing", entityLiving, POWERHAMMER_SOUNDDISTANCE, 1.0f, false, false, true, true,TGSoundCategory.GUN_FIRE), new TargetPoint(entityLiving.worldObj.provider.dimensionId, entityLiving.posX, entityLiving.posY, entityLiving.posZ, 50.0f));
				}
				
				return true;
			}
		}
		return false;
	}

	
	
	@Override
	public int consumeAmmoCharge(ItemStack item, float f, boolean creative) {
		int consume = (int) (this.ammoConsumedOnFullCharge*f);
		
		consume=MathUtil.clamp(consume, 1, this.ammoConsumedOnFullCharge);
		
		if(this.getCurrentAmmo(item)< consume){
			consume = this.getCurrentAmmo(item);
		}
		if(!creative){
			this.useAmmo(item, consume);
		}
		return consume;
		
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
			world.playSoundEffect(x+0.5D, y+0.5D, z+0.5D, "techguns:guns.powerhammerImpactGround", 1.0f, 1.0f);
			return true;
		}
		return false;
	}

	@Override
	public DamageType getDamageType() {
		return DamageType.PHYSICAL;
	}
	
	
}
