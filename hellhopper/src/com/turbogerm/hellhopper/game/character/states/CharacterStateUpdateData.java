package com.turbogerm.hellhopper.game.character.states;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.game.PlatformToCharCollisionData;
import com.turbogerm.hellhopper.game.RiseSection;
import com.turbogerm.hellhopper.game.enemies.EnemyBase;
import com.turbogerm.hellhopper.game.items.ItemBase;
import com.turbogerm.hellhopper.game.platforms.PlatformBase;

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
    public float delta;
}
