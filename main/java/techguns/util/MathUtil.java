package techguns.util;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class MathUtil {
	
	public static final double D2R = Math.PI/180.0;
	public static final double R2D = 180.0/Math.PI;
	
	/**
	 * sin with argument in degrees (0-360)
	 * @param arg
	 * @return
	 */
    public static float sin360(float arg){
    	return MathHelper.sin(arg / 180.0F * (float)Math.PI);
    }
    /**
	 * cos with argument in degrees (0-360)
	 * @param arg
	 * @return
	 */
    public static float cos360(float arg){
    	return MathHelper.cos(arg / 180.0F * (float)Math.PI);
    }
    
    public static float clamp(float value, float min, float max){
    	if(value<min){
    		return min;
    	} else if (value >max){
    		return max;
    	} else {
    		return value;
    	}
    }
    
    public static int clamp(int value, int min, int max){
    	if(value<min){
    		return min;
    	} else if (value >max){
    		return max;
    	} else {
    		return value;
    	}
    }
    
    public static int randomInt(Random rand, int min, int max) {
    	if (max >= min)
    		return min+rand.nextInt((max-min)+1);
    	else
    		return max+rand.nextInt((min-max)+1);
    }
    
    public static float randomFloat(Random rand, float min, float max) {
    	return min+(rand.nextFloat()*(max-min));
    }
    
    public static Vec2 polarOffsetXZ(double x, double z, double radius, double angle) {
    	x = x + (radius*Math.cos(angle));
    	z = z + (radius*Math.sin(angle));
    	
    	return new Vec2(x, z);
    }
    
    public static class Vec2{
    	public double x;
    	public double y;
    	
    	public Vec2(double x, double y){
    		this.x=x;
    		this.y=y;
    	}
    	
    	/**
    	 * returns the squared vector length;
    	 * @return
    	 */
    	public double lenSquared(){
    		return x*x + y*y;
    	}
    	
    	public double len(){
    		return Math.sqrt(this.lenSquared());
    	}
    	
    	/**
    	 * normalizes THIS vector
    	 */
    	public void normalize(){
    		double len = this.len();
    		if(len >0){
	    		double f = 1/len;
				x=x*f;
				y=y*f;
    		}
    	}
    	
    	/**
    	 * Gets a new vec2 that is a normalized version of THIS
    	 * @return
    	 */
    	public Vec2 getNormalized(){
    		double len = this.len();
    		if(len >0){
	    		double f = 1/len;
				return new Vec2(this.x*f, this.y*f);
    		}
    		return new Vec2(0,0);
    	}
    	
    	/**
    	 * Returns Vector A - Vector B
    	 * @param A
    	 * @param B
    	 * @return
    	 */
    	public static Vec2 substract(Vec2 A, Vec2 B){
    		return new Vec2(B.x-A.x, B.y-A.y);
    	}
    	
    	/**
    	 * Returns Vector A + Vector B
    	 * @param A
    	 * @param B
    	 * @return
    	 */
    	public static Vec2 add(Vec2 A, Vec2 B){
    		return new Vec2(B.x+A.x, B.y+A.y);
    	}
    	
    	/**
    	 * get new vector from THIS position to endpoint position
    	 * @param endpoint
    	 * @return
    	 */
    	public Vec2 getVecTo(Vec2 endpoint){
    		return substract(endpoint, this);
    	}
    	
    	/**
    	 * get new vector from startpoint position to THIS position
    	 * @param endpoint
    	 * @return
    	 */
    	public Vec2 getVecFrom(Vec2 startpoint){
    		return substract(this,startpoint);
    	}
    	
    	/**
    	 * return dot product with other vector: this dot other
    	 * @param other
    	 * @return
    	 */
    	public double dot(Vec2 other){
    		return (this.x*other.x+this.y*other.y);
    	}
    	
		/**
		 * get a new vec2 rotated by degree
		 */
    	public Vec2 getRotated(float degree){
    		double ynew = y*cos360(degree) - x*sin360(degree);
    		double xnew = y*sin360(degree) + x*cos360(degree);
    		return new Vec2(xnew,ynew);
    	}
    	/**
    	 * DOESN'T WORK?
    	 * get the angle in degree between 2 vectors;
    	 * @param A
    	 * @param B
    	 * @return
    	 */
    	@Deprecated
    	public static double getAngleBetween(Vec2 A, Vec2 B){
    		Vec2 vec1 = A.getNormalized();
    		Vec2 vec2 = B.getNormalized();
    		
    		return Math.acos(vec1.dot(vec2)/180.0F * (float)Math.PI);
    	}
    }
    
    public static MovingObjectPosition traceVector(World world, Vec3 src, Vec3 direction, double distance, int steps, Entity ent, EntityLivingBase shooter) {
    	direction = direction.normalize();
    	for (int i = 0; i < steps; i++) {
  			//System.out.println("StartPos:"+src.toString());
    		
    		//rayTraceBlocks fucks up the vectors?
    		Vec3 src2 = Vec3.createVectorHelper(src.xCoord, src.yCoord, src.zCoord);
    		
    		Vec3 dst = src.addVector(direction.xCoord*distance, direction.yCoord*distance, direction.zCoord*distance);
    		Vec3 dst2 = Vec3.createVectorHelper(dst.xCoord, dst.yCoord, dst.zCoord);
    		//System.out.printf("Trace step [%d]: SRC(%.1f / %.1f / %.1f) - DST(%.1f / %.1f / %.1f)\n",i, src.xCoord, src.yCoord, src.zCoord, dst.xCoord, dst.yCoord, dst.zCoord);
    		
    		MovingObjectPosition movingobjectposition = world.func_147447_a(src, dst, false, true, false); //world.rayTraceBlocks(, )
    		
    		//trace Against Entities
    	    Entity entity = null;
    	    
    	    double minX,maxX;
    	    double minY,maxY;
    	    double minZ,maxZ;
    	    
    	    if(src2.xCoord<dst2.xCoord){
    	    	minX=src2.xCoord;
    	    	maxX=dst2.xCoord;
    	    } else {
    	    	maxX=src2.xCoord;
    	    	minX=dst2.xCoord;
    	    }
    	    if(src2.yCoord<dst2.yCoord){
    	    	minY=src2.yCoord;
    	    	maxY=dst2.yCoord;
    	    } else {
    	    	maxY=src2.yCoord;
    	    	minY=dst2.yCoord;
    	    }
    	    if(src2.zCoord<dst2.zCoord){
    	    	minZ=src2.zCoord;
    	    	maxZ=dst2.zCoord;
    	    } else {
    	    	maxZ=src2.zCoord;
    	    	minZ=dst2.zCoord;
    	    }
    	    
    	    
    	    AxisAlignedBB box = AxisAlignedBB.getBoundingBox(minX,minY,minZ,maxX,maxY,maxZ).expand(1.0f, 1.0f, 1.0f);
            List list = world.getEntitiesWithinAABBExcludingEntity(ent, box); //ent.boundingBox.addCoord(distance,distance,distance).expand(1.0D, 1.0D, 1.0D)
            double d0 = 0.0D;

            MovingObjectPosition mop = null;
            
            for (int j = 0; j < list.size(); ++j)
            {
                Entity entity1 = (Entity)list.get(j);
                
                if (entity1.canBeCollidedWith() && !entity1.isDead && (entity1 != shooter))
                {
                
                	//System.out.println("checking:"+entity1+" "+src2+"->"+dst2);
                	
                    float f = 0.3F;
                    AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double)f, (double)f, (double)f);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(src2, dst2);

                    //System.out.println("Intercept:"+movingobjectposition1);
                    
                    if (movingobjectposition1 != null)
                    {
                        double d1 = src2.distanceTo(movingobjectposition1.hitVec);

                        if (d1 < d0 || d0 == 0.0D)
                        {
                            entity = entity1;
                            mop = movingobjectposition1;
                            d0 = d1;
                        }
                    }
                    
                    
                }
            }

            if (entity != null)
            {
            	//System.out.println("Hit entity");
                movingobjectposition = new MovingObjectPosition(entity,mop.hitVec);
            }
    		
    		
    		
    		if (movingobjectposition != null) {
    			//System.out.println("Trace step ["+i+"]: "+ movingobjectposition.typeOfHit.toString());
    			//System.out.println("HitPos:"+movingobjectposition.hitVec.toString());
    			return movingobjectposition;
    		}
    		src = Vec3.createVectorHelper(dst.xCoord, dst.yCoord, dst.zCoord);
    	}
           
        return null;
    }
    
    /**
     * Absolute value of integer
     * @param i
     */
    public static int abs(int i){
    	if(i<0){
    		return -i;
    	}
    	return i;
    }
    
    /**
	  * Copied from RenderLivingBase
	  * 
   * Returns a rotation angle that is inbetween two other rotation angles. par1 and par2 are the angles between which
   * to interpolate, par3 is probably a float between 0.0 and 1.0 that tells us where "between" the two angles we are.
   * Example: par1 = 30, par2 = 50, par3 = 0.5, then return = 40
   */
  public static float interpolateRotation(float par1, float par2, float partialTickTime)
  {
      float f3;

      for (f3 = par2 - par1; f3 < -180.0F; f3 += 360.0F)
      {
          ;
      }

      while (f3 >= 180.0F)
      {
          f3 -= 360.0F;
      }

      return par1 + partialTickTime * f3;
  }
  
  /**
   * Rotates the vector around the x axis by the specified angle (in radians).
   */
  public static void rotateAroundX(Vec3 vec, double angle)
  {
      double dc = Math.cos(angle);
      double ds = Math.sin(angle);
      double d0 = vec.xCoord;
      double d1 = vec.yCoord * (double)dc + vec.zCoord * (double)ds;
      double d2 = vec.zCoord * (double)dc - vec.yCoord * (double)ds;
      vec.xCoord = d0;
      vec.yCoord = d1;
      vec.zCoord = d2;
  }

  /**
   * Rotates the vector around the y axis by the specified angle (in radians).
   */
  public static void rotateAroundY(Vec3 vec, double angle)
  {
      double dc = Math.cos(angle);
      double ds = Math.sin(angle);
      double d0 = vec.xCoord * (double)dc + vec.zCoord * (double)ds;
      double d1 = vec.yCoord;
      double d2 = vec.zCoord * (double)dc - vec.xCoord * (double)ds;
      vec.xCoord = d0;
      vec.yCoord = d1;
      vec.zCoord = d2;
  }

  /**
   * Rotates the vector around the z axis by the specified angle (in radians).
   */
  public static void rotateAroundZ(Vec3 vec, double angle)
  { 
      double dc = Math.cos(angle);
      double ds = Math.sin(angle);
      double d0 = vec.xCoord * (double)dc + vec.yCoord * (double)ds;
      double d1 = vec.yCoord * (double)dc - vec.xCoord * (double)ds;
      double d2 = vec.zCoord;
      vec.xCoord = d0;
      vec.yCoord = d1;
      vec.zCoord = d2;
  }
  
  /**
   * return if val is in interval of min to max, including min and max.
   * @param val
   * @param min
   * @param max
   * @return
   */
  public static boolean inRange(int val, int min, int max){
	  return val>=min && val <=max;
  }
}
