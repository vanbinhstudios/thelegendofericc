# The Legend of Ericc

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/aec03650a4df457db7e2bb385dc3c6f8)](https://app.codacy.com/app/vanbinhstudios/thelegendofericc?utm_source=github.com&utm_medium=referral&utm_content=vanbinhstudios/thelegendofericc&utm_campaign=Badge_Grade_Settings)


## The Game

The Legend of Ericc is a turn-based single player rogue-like game made in Java with the help of ECS in Ashley. Our team wanted to try something new so we picked up game designing and we learn on the fly, so any suggestions would be highly appreciated. You can download and play our game (or even drop us some tests!) for free.

## Log

### 24.03.19
We wanted our dungeon to have a lot of different levels, but for one specific level to be small, so we focused on that task and ended up with stairs which can lead you down and up through the dark!

![Alt Text](https://media.giphy.com/media/37soZwCt7cuhDE8WEr/giphy.gif)

### 22.03.19
During this week we focused on entities interaction system (or at least to some extent - we'd like to implement combat system the following week). The first iteration of that is shown on the gif under, we can now move crates and collide with other entities (disclaimer: the animation branch have not been merged into dev at that time, so that is why you can not see them here)

![Alt Text](https://media.giphy.com/media/1fiGakAmjGEjra4e4b/giphy.gif)

### 20.03.19
After a brief research we ended up with this little fade in/out animation for sprites when the player moves (currently WIP, we might implement other system first and then come back to this one)

![Alt Text](https://media.giphy.com/media/833DfjMBioBSY9JgdY/giphy.gif)

### 17.03.19
We decided to implement FOV and fog of war, this is the result (there are some animations that we need to add in later sprints),
we based our fov algorithm on raytracing

![Alt Text](https://media.giphy.com/media/9AIXNkGQFPCtiQTh1p/giphy.gif)

### 16.03.19
After the first sprint we ended up with some stupid mobs (rendering is done real time, the mobs can turn only after the player makes a move, there are no mobs collision yet)

![Alt Text](https://media.giphy.com/media/Zy9k0cvt6piTgrtTrl/giphy.gif)

### 10.03.19
For our first procedural map generation we implemented BSP algorithm, we do consider chaning it to tunelling one.

![alt text](https://sites.google.com/site/jicenospam/dungeon_bsp2.png "BSP2")


## Assets
### Technology used
-  Java
-  Intellij
-  Ashley
- Codacy
### Misc
-  <a href="https://pixel-poem.itch.io/dungeon-assetpuck">Sprites</a>
