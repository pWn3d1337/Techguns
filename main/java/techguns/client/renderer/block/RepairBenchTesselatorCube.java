package techguns.client.renderer.block;

import net.minecraft.util.ResourceLocation;

public class RepairBenchTesselatorCube extends TesselatorCube {

	public RepairBenchTesselatorCube() {
		this("textures/blocks/repairbench_textures.png");
	}
	
	public RepairBenchTesselatorCube(String texture) {
		super();
		this.texture = new ResourceLocation("techguns", texture);
		this.t_x0=0.0D;
		this.t_x1=0.5D;
		this.t_y0=0.5D;
		this.t_y1=1.0D;
		
		this.f_x0=0.5D;
		this.f_x1=1.0D;
		this.f_y0=0.0D;
		this.f_y1=0.5D;
		
		this.ba_x1=0.5D;
		this.ba_y1=0.5D;
		this.l_x1=0.5D;
		this.l_y1=0.5D;
		this.r_x1=0.5D;
		this.r_y1=0.5D;
		this.bo_x1=0.5D;
		this.bo_y1=0.5D;
	}
}
