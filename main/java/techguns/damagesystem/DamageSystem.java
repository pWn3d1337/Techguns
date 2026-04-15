package techguns.damagesystem;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import techguns.TGConfig;
import techguns.entities.npc.NPCTurret;
import techguns.items.armors.GenericArmor;
import techguns.util.EntityDeathUtils.DeathType;
import techguns.util.MathUtil;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class DamageSystem {
	private static Random rnd = new Random();
	
	private static Field ELB_EntityAge;
	private static Field ELB_lastDamage;
	private static Field ELB_recentlyHit;
	private static Field ELB_attackingPlayer;
	
	//private static Method ELB_Method_damageEntity;
	private static Method ELB_Method_setBeenAttacked;
	private static Method ELB_Method_getDeathSound;
	private static Method ELB_Method_getHurtSound;
	private static Method ELB_Method_getSoundVolume;
	private static Method ELB_Method_getSoundPitch;
	private static Method ELB_Method_applyPotionDamageCalculations;
	private static Method ELB_Method_damageArmor;
	
	static{
			ELB_EntityAge = ReflectionHelper.findField(EntityLivingBase.class, "entityAge","field_70708_bq");
			//ELB_EntityAge.setAccessible(true); Reflectionhelper already sets to accesible
			
			ELB_lastDamage = ReflectionHelper.findField(EntityLivingBase.class, "lastDamage","field_110153_bc");
			//ELB_lastDamage.setAccessible(true);
			
			ELB_recentlyHit = ReflectionHelper.findField(EntityLivingBase.class, "recentlyHit","field_70718_bc");
			
			ELB_attackingPlayer = ReflectionHelper.findField(EntityLivingBase.class, "attackingPlayer","field_70717_bb");
			
			//instance parameter is pointless
			//ELB_Method_damageEntity = ReflectionHelper.findMethod(EntityLivingBase.class, null, new String[]{"damageEntity","d"}, DamageSource.class, float.class);
			ELB_Method_setBeenAttacked = ReflectionHelper.findMethod(EntityLivingBase.class, null, new String[]{"setBeenAttacked","func_70018_K"});
			ELB_Method_getDeathSound = ReflectionHelper.findMethod(EntityLivingBase.class, null, new String[]{"getDeathSound","func_70673_aS"});
			ELB_Method_getHurtSound = ReflectionHelper.findMethod(EntityLivingBase.class, null, new String[]{"getHurtSound","func_70621_aR"});
			ELB_Method_getSoundVolume = ReflectionHelper.findMethod(EntityLivingBase.class, null, new String[]{"getSoundVolume","func_70599_aP"});
			ELB_Method_getSoundPitch = ReflectionHelper.findMethod(EntityLivingBase.class, null, new String[]{"getSoundPitch","func_70647_i"});
			ELB_Method_applyPotionDamageCalculations = ReflectionHelper.findMethod(EntityLivingBase.class, null, new String[]{"applyPotionDamageCalculations","func_70672_c"}, DamageSource.class, float.class);
			ELB_Method_damageArmor = ReflectionHelper.findMethod(EntityLivingBase.class, null, new String[]{"damageArmor","func_70675_k"}, float.class);
	}
	
	public static float getDamageFactor(EntityLivingBase attacker, EntityLivingBase target){
//		System.out.println("Damage Factor "+attacker+" attacked "+ target);
		
		if (attacker instanceof EntityPlayer && target instanceof EntityPlayer){
					
			if (MinecraftServer.getServer().isPVPEnabled()){
//				System.out.println("Player Hit Player");
				return TGConfig.damagePvP;
			} else {
				return 0.0f;
			}
		} else if (target instanceof EntityPlayer){
			if ( attacker instanceof NPCTurret){
	//			System.out.println("Turret Hit Player");
				return TGConfig.damageTurretToPlayer;
			} else {
//				System.out.println("NPC Hit Player");
				return TGConfig.damageFactorNPC;
			}

		} else if (attacker instanceof EntityPlayer){
	//		System.out.println("Player hit NPC");
			return 1.0f;
		}
		
//		System.out.println("NPC hit NPC");
		
		return TGConfig.damageFactorNPC;
	}
	
	
	
	
	public static float getTotalArmorAgainstType(EntityPlayer ply, DamageType type){
		float value=0.0f;
		
		for(int i=0;i<4; i++){
			ItemStack armor = ply.inventory.armorInventory[i];
			if(armor!=null){
				Item item = armor.getItem();
				
				if(item instanceof GenericArmor){
					value+=((GenericArmor)item).getArmorValue(armor, type);				
				} else if (item instanceof ItemArmor){
					if(type==DamageType.PHYSICAL){
						value += ((ItemArmor) item).getArmorMaterial().getDamageReductionAmount(((ItemArmor)item).armorType);
					}
				}
				
				
			}
			
		}
		
		return value;
	}
	
	public static float getAdditionalReduction(EntityLivingBase target, DamageType damageType){
		//ItemStack[] armorInv = new ItemStack[]{target.getEquipmentInSlot(1), target.getEquipmentInSlot(2), target.getEquipmentInSlot(3), target.getEquipmentInSlot(4)}; 
		
		
		if(damageType == DamageType.FIRE){
			if (target.isImmuneToFire() /*|| target.getActivePotionEffect(Potion.fireResistance) != null*/){
				return 0.5f;
			}
		} 
		
		return 1.0f;
	}
	
	
	public static double getReduction(TGDamageSource src, double damage, float armorValue){
		/*if(src.damageType == DamageType.UNRESISTABLE){
			return 0.0D;
		}*/	
		double damageUnblocked = damage *(src.armorPenetration);
		
	//	System.out.println("damageUnblocked"+damageUnblocked);
		
		double damageBlocked = damage * (1.0D-src.armorPenetration);
		
	//	System.out.println("damageBlocked"+damageBlocked);
		
		damageBlocked = damageBlocked*(25.0D-armorValue)*0.04D;
		
	//	System.out.println("reducedDamageBlocked:"+damageBlocked);
		//float armor = MathUtil.clamp(armorValue,0.0f,GenericArmor.MAX_ARMOR*1.0f);
        //double reductionFactor = (armor/GenericArmor.MAX_ARMOR*1.0f) * GenericArmor.getArmorCap(src.damageType) * (1.0D-src.armorPenetration);
		
		return (damage-damageUnblocked-damageBlocked)/damage;
	}
	
	public static void damageEntity(EntityClientPlayerMP clientply, TGDamageSource dmgsrc, float damage){
		//System.out.println("DamageClientPlayer:"+clientply);
		if (!clientply.isEntityInvulnerable())
        {
            clientply.setHealth(clientply.getHealth() - damage);
        }
	}
	
	public static void damageEntityPlayer(EntityPlayer ply, TGDamageSource dmgsrc, float damage){
		//System.out.println("DamagePlayer:"+ply);
	        if (!ply.isEntityInvulnerable())
	        {
	            damage = ForgeHooks.onLivingHurt(ply, dmgsrc, damage);
	            if (damage <= 0) return;
	            
	            if (/*!dmgsrc.isUnblockable() &&*/ dmgsrc.damageType == DamageType.PHYSICAL && ply.isBlocking() && damage > 0.0F)
	            {
	                damage = (1.0F + damage) * 0.5F;
	            }

	           // System.out.println("DamageBeforeArmor:"+damage);
	            damage = ArmorProperties.ApplyArmor(ply, ply.inventory.armorInventory, dmgsrc, damage);
	           // System.out.println("DamageAFTERArmor:"+damage);
	            if (damage <= 0) return;
	            
	            //damage = ply.applyPotionDamageCalculations(dmgsrc, damage);
	            try {
					damage = (float) ELB_Method_applyPotionDamageCalculations.invoke(ply, dmgsrc,damage);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            float f1 = damage;
	            damage = Math.max(damage - ply.getAbsorptionAmount(), 0.0F);
	            ply.setAbsorptionAmount(ply.getAbsorptionAmount() - (f1 - damage));

	            if (damage != 0.0F)
	            {
	                ply.addExhaustion(dmgsrc.getHungerDamage());
	                float f2 = ply.getHealth();
	                ply.setHealth(ply.getHealth() - damage);
	                ply.func_110142_aN().func_94547_a(dmgsrc, f2, damage);
	            }
	        }
	}
	
	public static void damageEntity(EntityLivingBase elb, TGDamageSource dmgsrc, float damage){
		//System.out.println("DamageEntity:"+elb);
		 if (!elb.isEntityInvulnerable())
	        {
	            damage = ForgeHooks.onLivingHurt(elb, dmgsrc, damage);
	            if (damage <= 0) return;
	            
	            //damage = elb.applyArmorCalculations(dmgsrc, damage);
	            //damage = elb.applyPotionDamageCalculations(dmgsrc, damage);
	            damage = (float) DamageSystem.getDamage(dmgsrc, damage, elb);
	            try {
					damage = (float) ELB_Method_applyPotionDamageCalculations.invoke(elb, dmgsrc,damage);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            float f1 = damage;
	            damage = Math.max(damage - elb.getAbsorptionAmount(), 0.0F);
	            elb.setAbsorptionAmount(elb.getAbsorptionAmount() - (f1 - damage));

	            if (damage != 0.0F)
	            {
	                float f2 = elb.getHealth();
	                elb.setHealth(f2 - damage);
	                elb.func_110142_aN().func_94547_a(dmgsrc, f2, damage);
	                elb.setAbsorptionAmount(elb.getAbsorptionAmount() - damage);
	            }
	        }
	}
	
	public static double getDamage(TGDamageSource src, float damage, Entity ent){	
		if(ent instanceof EntityLivingBase){
			EntityLivingBase target = (EntityLivingBase) ent;
			float penetration = 0.0f;
			if(src.damageType == DamageType.UNRESISTABLE){
				return damage;
			}
			
			float armor;
			if(ent instanceof INpcTGDamageSystem){
				armor = ((INpcTGDamageSystem) ent).getTotalArmorAgainstType(src);
				penetration = MathUtil.clamp(src.armorPenetration-((INpcTGDamageSystem) ent).getPenetrationResistance(src), 0.0f, 1.0f);
			} else {
			  armor = MathUtil.clamp(target.getTotalArmorValue(),0,(int)GenericArmor.MAX_ARMOR);
			  armor = getArmorAgainstDamageTypeDefault(target, armor, src.damageType);
			}
			
			//penetration = calcPenetration(target, penetration, src);
			
			double damageBlocked = damage * (1-penetration) * (25.0F-armor);
			try {
				ELB_Method_damageArmor.invoke(ent, (float)damage);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
	        /*double reductionFactor = (armor/GenericArmor.MAX_ARMOR) * GenericArmor.getArmorCap(src.damageType);
	 		damageBlocked*=(1-reductionFactor);*/
			damageBlocked *= 0.04D; // = 1/25
	        
	        //float f2 = getAdditionalReduction(target, src.damageType);
	 		
	        double totaldmg = ((damage*penetration) +damageBlocked); 
	        /*if (totaldmg>=1.0f){
	        	return totaldmg;
	        } else {
	        	double chance = 1.0D-totaldmg;
	        	MathUtil.clamp((float)chance, 0.25f, 1.0f);
	        	if (rnd.nextFloat()<chance){
	        		return 1.0D;
	        	}
	        	return 0.0D;
	        }*/
	        return totaldmg;
		} else {
			return damage;
		}
	}
	
	public static float calcPenetration(EntityLivingBase target, float penetration, TGDamageSource src){
		Entity attacker = src.getAttacker();
		//System.out.println("Penetration:"+target.toString()+"->"+(attacker==null?"null":attacker.toString()));
		if( target instanceof EntityPlayer){
	
			
			if( attacker !=null && attacker instanceof EntityPlayer) {
	//			System.out.println("PVP");
				penetration *= TGConfig.armorPenPvP;
			} else {
	//			System.out.println("EvP");
				penetration *= TGConfig.armorPenNPCtoPlayer;
			}

		} else {
	//		System.out.println("XvE");
		}
		return MathUtil.clamp(penetration,0F,1F);
	}
	
	/**
	 * Default behavior when unspecified
	 */
	public static float getArmorAgainstDamageTypeDefault(EntityLivingBase elb, float armor, DamageType damageType){
		switch(damageType){
			case PHYSICAL:
			case PROJECTILE:
				return armor;
				
			case EXPLOSION:
			case ENERGY:
			case ICE:
			case LIGHTNING:
				return Math.round(armor*0.5f);
			case FIRE:
				if(elb.isImmuneToFire()){
					return armor*2;
				} else {
					return armor*0.5f;
				}
				
			case POISON:
				return 0;
			case RADIATION:
				return 0;
			case UNRESISTABLE:
			default:
				return 0;
		}
		
	}
	
	public static void onLivingAttackEntityLivingBase(EntityLivingBase elb, DamageSource src, float damage){
		TGDamageSource dmgsrc = TGDamageSource.getFromGenericDamageSource(src);
		//damage = (float) DamageSystem.getDamage(dmgsrc, damage,elb);
		
		 if (elb.isEntityInvulnerable())
	        {
	            return;
	        }
	        else if (elb.worldObj.isRemote)
	        {
	            return;
	        }
	        else
	        {
	         try{
	        	
	        	//elb.entityAge = 0;
	        	ELB_EntityAge.setInt(elb, 0);
	            if (elb.getHealth() <= 0.0F)
	            {
	                return;
	            }
	            else if (dmgsrc.isFireDamage() && elb.isPotionActive(Potion.fireResistance))
	            {
	                return;
	            }
	            else
	            {
	                if ((dmgsrc.getDamageType().equals(DamageSource.anvil.getDamageType()) ||dmgsrc.getDamageType().equals(DamageSource.fallingBlock.getDamageType())) && elb.getEquipmentInSlot(4) != null)
	                {
	                    //elb.getEquipmentInSlot(4).damageItem((int)(damage * 4.0F + elb.rand.nextFloat() * damage * 2.0F), elb);
	                	elb.getEquipmentInSlot(4).damageItem((int)(damage * 4.0F + rnd.nextFloat() * damage * 2.0F), elb);
	                    damage *= 0.75F;
	                }

	                elb.limbSwingAmount = 1.5F;
	                boolean flag = true;

	                if ( !dmgsrc.ignoreHurtresistTime && ((float)elb.hurtResistantTime > (float)elb.maxHurtResistantTime / 2.0F))
	                {
	                    //if (damage <= elb.lastDamage)
	                	if(damage<= ELB_lastDamage.getFloat(elb))
	                    {
	                        return;
	                    }

	                    //elb.damageEntity(dmgsrc, damage - elb.lastDamage);
	                    //ELB_Method_damageEntity.invoke(elb, dmgsrc, damage - ELB_lastDamage.getFloat(elb));
	                	// System.out.println("Damaging Ent:"+elb.toString());
	                	
	                	if (elb instanceof EntityPlayer){
	                		DamageSystem.damageEntityPlayer((EntityPlayer)elb, dmgsrc, damage);
	                	} else {
	                		DamageSystem.damageEntity(elb,dmgsrc,damage);
	                	}
	                	
	                    //elb.lastDamage = damage;
	                    ELB_lastDamage.setFloat(elb,damage);
	                    flag = false;
	                }
	                else
	                {
	                    //elb.lastDamage = damage;
	                    
	                    elb.prevHealth = elb.getHealth();
	                    if(!dmgsrc.ignoreHurtresistTime){
		                    ELB_lastDamage.setFloat(elb,damage);
		                    elb.hurtResistantTime = elb.maxHurtResistantTime;
	                    }
	                    //elb.damageEntity(dmgsrc, damage);
	                    //ELB_Method_damageEntity.invoke(elb, dmgsrc, damage);
	                   // System.out.println("Damaging Ent:"+elb.toString());
	                	if (elb instanceof EntityPlayer){
	                		DamageSystem.damageEntityPlayer((EntityPlayer)elb, dmgsrc, damage);
	                	} else {
	                		DamageSystem.damageEntity(elb,dmgsrc,damage);
	                	}
	                    if(!dmgsrc.ignoreHurtresistTime){
	                    	elb.hurtTime = elb.maxHurtTime = 10;
	                    }
	                }

	                elb.attackedAtYaw = 0.0F;
	                Entity entity = dmgsrc.getEntity();

	                if (entity != null)
	                {
	                    if (entity instanceof EntityLivingBase)
	                    {
	                        elb.setRevengeTarget((EntityLivingBase)entity);
	                    }

	                    if (entity instanceof EntityPlayer)
	                    {
	                        //elb.recentlyHit = 100;
	                        //elb.attackingPlayer = (EntityPlayer)entity;
	                    	ELB_recentlyHit.setInt(elb, 100);
	                    	ELB_attackingPlayer.set(elb, (EntityPlayer)entity);
	                    }
	                    else if (entity instanceof net.minecraft.entity.passive.EntityTameable)
	                    {
	                        net.minecraft.entity.passive.EntityTameable entitywolf = (net.minecraft.entity.passive.EntityTameable)entity;

	                        if (entitywolf.isTamed())
	                        {
	                            //elb.recentlyHit = 100;
	                            //elb.attackingPlayer = null;
	                            ELB_recentlyHit.setInt(elb, 100);
		                    	ELB_attackingPlayer.set(elb, (EntityPlayer)null);
	                        }
	                    }
	                }

	                if (flag)
	                {
	                    elb.worldObj.setEntityState(elb, (byte)2);

	                    if (dmgsrc != DamageSource.drown)
	                    {
	                        //elb.setBeenAttacked();
	                        ELB_Method_setBeenAttacked.invoke(elb);
	                    }

	                    if (dmgsrc.hasKnockback() && entity != null)
	                    {
	                        double d1 = entity.posX - elb.posX;
	                        double d0;

	                        for (d0 = entity.posZ - elb.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D)
	                        {
	                            d1 = (Math.random() - Math.random()) * 0.01D;
	                        }

	                        elb.attackedAtYaw = (float)(Math.atan2(d0, d1) * 180.0D / Math.PI) - elb.rotationYaw;
	                        elb.knockBack(entity, damage*dmgsrc.knockbackMultiplier, d1, d0);
	                    }
	                    else
	                    {
	                        elb.attackedAtYaw = (float)((int)(Math.random() * 2.0D) * 180);
	                    }
	                }

	                String s;

	                if (elb.getHealth() <= 0.0F)
	                {
	                    //s = elb.getDeathSound();
	                	s = (String) ELB_Method_getDeathSound.invoke(elb);
	                	
	                    if (flag && s != null)
	                    {
	                    	float volume = (float) ELB_Method_getSoundVolume.invoke(elb);
	                    	float pitch = (float) ELB_Method_getSoundPitch.invoke(elb);
	                    	
	                        //elb.playSound(s, elb.getSoundVolume(), elb.getSoundPitch());
	                    	elb.playSound(s, volume, pitch);
	                    }

	                    if (rnd.nextFloat()>dmgsrc.goreChance){
	        				dmgsrc.deathType = DeathType.DEFAULT;
	        			}
	                    
	                    elb.onDeath(dmgsrc);
	                }
	                else
	                {
	                    //s = elb.getHurtSound();
	                    s = (String) ELB_Method_getHurtSound.invoke(elb);

	                    if (flag && s != null)
	                    {
	                    	float volume = (float) ELB_Method_getSoundVolume.invoke(elb);
	                    	float pitch = (float) ELB_Method_getSoundPitch.invoke(elb);
	                    	
	                        //elb.playSound(s, elb.getSoundVolume(), elb.getSoundPitch());
	                        elb.playSound(s, volume, pitch);
	                    }
	                }

	                return;
	            }
	        
	        } catch (IllegalArgumentException | IllegalAccessException e) {
	        	e.printStackTrace();
	        } catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
	public static void onLivingAttackPlayer(EntityPlayer player, DamageSource dmgsrc, float damage){
		 if (player.isEntityInvulnerable())
	        {
	            return;
	        }
	        else if (player.capabilities.disableDamage && !dmgsrc.canHarmInCreative())
	        {
	            return;
	        }
	        else
	        {
	            //player.entityAge = 0;
	        	try {
						ELB_EntityAge.setInt(player, 0);
					
		        	
		            if (player.getHealth() <= 0.0F)
		            {
		                return;
		            }
		            else
		            {
		                if (player.isPlayerSleeping() && !player.worldObj.isRemote)
		                {
		                    player.wakeUpPlayer(true, true, false);
		                }
	
		                if (dmgsrc.isDifficultyScaled())
		                {
		                    if (player.worldObj.difficultySetting == EnumDifficulty.PEACEFUL)
		                    {
		                        damage = 0.0F;
		                    }
	
		                    if (player.worldObj.difficultySetting == EnumDifficulty.EASY)
		                    {
		                        damage = damage / 2.0F + 1.0F;
		                    }
	
		                    if (player.worldObj.difficultySetting == EnumDifficulty.HARD)
		                    {
		                        damage = damage * 3.0F / 2.0F;
		                    }
		                }
	
		                if (damage == 0.0F)
		                {
		                    return;
		                }
		                else
		                {
		                    Entity entity = dmgsrc.getEntity();
	
		                    if (entity instanceof EntityArrow && ((EntityArrow)entity).shootingEntity != null)
		                    {
		                        entity = ((EntityArrow)entity).shootingEntity;
		                    }
	
		                    player.addStat(StatList.damageTakenStat, Math.round(damage * 10.0F));
		                    
		                    //
		                    //return super.attackEntityFrom(p_70097_1_, p_70097_2_);
		                    onLivingAttackEntityLivingBase(player, dmgsrc, damage);
		                }
		            }
		            
		        } catch (IllegalArgumentException | IllegalAccessException e) {
		        	e.printStackTrace();
		        }
	        }
	}
	
}
