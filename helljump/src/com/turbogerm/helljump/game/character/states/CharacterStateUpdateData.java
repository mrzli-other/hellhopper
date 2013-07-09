package com.turbogerm.helljump.game.character.states;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.helljump.game.PlatformToCharCollisionData;
import com.turbogerm.helljump.game.RiseSection;
import com.turbogerm.helljump.game.character.CharacterEffects;
import com.turbogerm.helljump.game.enemies.EnemyBase;
import com.turbogerm.helljump.game.items.ItemBase;
import com.turbogerm.helljump.game.platforms.PlatformBase;

public final class CharacterStateUpdateData {
    
    public Vector2 characterPosition;
    public Vector2 characterSpeed;
    public float horizontalSpeed;
    public PlatformToCharCollisionData platformToCharCollisionData;
    public Array<RiseSection> activeRiseSections;
    public Array<PlatformBase> visiblePlatforms;
    public Array<EnemyBase> visibleEnemies;
    public Array<ItemBase> visibleItems;
    public float riseHeight;
    public float visibleAreaPosition;
    public CharacterEffects characterEffects;
    public float delta;
}
