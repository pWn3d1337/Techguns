package techguns.client.audio;

public enum TGSoundCategory {
	GUN_FIRE("gunfire",0),
	PLAYER_EFFECT("playereffect",1),
	RELOAD("reload",2),
	EXPLOSION("explosion",3),
	MACHINE("machine",4),
	DEATHEFFECT("deathFX",5);
	
	private int id;
	private String name;
	private TGSoundCategory(String name,int id) {
		this.id = id;
		this.name = name;
	}
}
