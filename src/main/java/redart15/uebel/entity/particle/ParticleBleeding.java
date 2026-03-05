package redart15.uebel.entity.particle;

import net.minecraft.client.entity.particle.Particle;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.Global;
import net.minecraft.core.world.World;

import static redart15.uebel.UebelMod.*;

public class ParticleBleeding extends Particle {
	public static final String SPLAT_PATH = MOD_ID + ":particle/bleeding/blood_splat_";
	protected final float originalScale;
	protected IconCoordinate splat;
	private double adjust;

	public ParticleBleeding(World world, double x, double y, double z, double xa, double ya, double za) {
		super(world, x, y, z, xa, ya, za);
		this.xd = this.xd * 0.1;
		this.yd = this.yd * 0.1;
		this.zd = this.zd * 0.1;
		this.xd += xa;
		this.yd += ya;
		this.zd += za;
		this.tex = TextureRegistry.getTexture(MOD_ID + ":particle/bleeding/blood");
		this.splat = TextureRegistry.getTexture(SPLAT_PATH + this.random.nextInt(4));
		this.originalScale = this.size;
		this.gravity = 0.2f;
		this.lifetime = 20 * Global.TICKS_PER_SECOND;
		this.adjust = 0.01 * this.random.nextFloat();
	}

	@Override
	public void tick() {
		super.tick();
		if (this.onGround) {
			this.xd = this.yd = this.zd = 0;
		}
	}

	@Override
	public void render(
		Tessellator t, float partialTick,
		double xOff, double yOff, double zOff,
		float xa, float ya, float za,
		float xa2, float za2
	) {
		if (this.tex == null) {
			return;
		}
		if (this.isInWater() || this.isInLava()) {
			this.remove();
		}
		if (this.onGround) {
			this.renderBloodSplat(t, partialTick, xOff, yOff, zOff, xa, ya, za, xa2, za2);
		} else {
			this.renderBloodDrop(t, partialTick, xOff, yOff, zOff, xa, ya, za, xa2, za2);
		}
	}

	private void renderBloodSplat(Tessellator t, float partialTick, double xOff, double yOff, double zOff, float xa, float ya, float za, float xa2, float za2) {
		float s = (this.age + partialTick) / this.lifetime;
		this.size = this.originalScale * (0.6F + s * s * 0.4f);
		float minU = (float) this.splat.getIconUMin();
		float maxU = (float) this.splat.getIconUMax();
		float minV = (float) this.splat.getIconVMin();
		float maxV = (float) this.splat.getIconVMax();
		float r = 0.1F * this.size;
		float x = (float) (this.xo + (this.x - this.xo) * partialTick - xOff);
		float y = (float) (this.yo + (this.y - this.yo) * partialTick - yOff);
		float z = (float) (this.zo + (this.z - this.zo) * partialTick - zOff);
		float br = 1.0F;
		if (LightmapHelper.isLightmapEnabled()) {
			t.setLightmapCoord(this.getLightmapCoord(partialTick));
		} else {
			br = this.getBrightness(partialTick);
		}
		t.setColorOpaque_F(this.rCol * br, this.gCol * br, this.bCol * br);
		// top side
		t.addVertexWithUV(x - r, y - 0.05 + adjust, z - r, maxU, maxV);
		t.addVertexWithUV(x - r, y - 0.05 + adjust, z + r, maxU, minV);
		t.addVertexWithUV(x + r, y - 0.05 + adjust, z + r, minU, minV);
		t.addVertexWithUV(x + r, y - 0.05 + adjust, z - r, minU, maxV);
		// bottom side
		t.addVertexWithUV(x - r, y - 0.05 + adjust, z - r, maxU, maxV);
		t.addVertexWithUV(x + r, y - 0.05 + adjust, z - r, maxU, minV);
		t.addVertexWithUV(x + r, y - 0.05 + adjust, z + r, minU, minV);
		t.addVertexWithUV(x - r, y - 0.05 + adjust, z + r, minU, maxV);
	}

	private void renderBloodDrop(Tessellator t, float partialTick, double xOff, double yOff, double zOff, float xa, float ya, float za, float xa2, float za2) {
		float u0 = (float) this.tex.getIconUMin();
		float u2 = (float) this.tex.getIconUMax();
		float v0 = (float) this.tex.getIconVMin();
		float offset;
		int stage = this.age / 4 / 2;
		switch (stage) {
			case 0:
				offset = 1 / 4.0f;
				break;
			case 1:
				offset = 2 / 4.0f;
				break;
			case 2:
				offset = 3 / 4.0f;
				break;
			case 3:
			default:
				offset = 1.0f;
		}
		float v2 = (float) this.tex.getSubIconV(offset);
		float r = 0.1F / 2.0F * this.size;
		float x = (float) (this.xo + (this.x - this.xo) * partialTick - xOff);
		float y = (float) (this.yo + (this.y - this.yo) * partialTick - yOff);
		float z = (float) (this.zo + (this.z - this.zo) * partialTick - zOff);
		float br = 1.0F;

		if (LightmapHelper.isLightmapEnabled()) {
			t.setLightmapCoord(this.getLightmapCoord(partialTick));
		} else {
			br = this.getBrightness(partialTick);
		}
		t.setColorOpaque_F(this.rCol * br, this.gCol * br, this.bCol * br);
		float rxa = xa * r;
		float rza = za * r;
		float rya = ya * r * offset;
		t.addVertexWithUV(x - rxa, y - rya, z - rza, u2, v2);
		t.addVertexWithUV(x - rxa, y + rya, z - rza, u2, v0);
		t.addVertexWithUV(x + rxa, y + rya, z + rza, u0, v0);
		t.addVertexWithUV(x + rxa, y - rya, z + rza, u0, v2);
	}
}
