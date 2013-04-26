To change the physics (i.e. gravity, viscosity) of your game, you simply need to change the ForceBundle object. 

Currently, ForceBundle comprises a Gravity force and a Viscosity force. Those forces have been generously provided 
by our framework. If you don't want those Forces, then take them out of the ForceBundle. However, if you want some special 
force that we haven't implemented in scroller.util.package, you simply need to create it. Make sure that any Force you 
create implements the Force interface. Please see Gravity.java or Viscosity.java in scroller.util.package for examples 
of how to write a Force. Then add that force into your ForceBundle. 

If you want different Sprites to be affected by different forces - not a Problem. You can make a PlayerForceBundle.java 
that handles all the force for a player and a EnemyForceBundle.java that handles all the forces for an Enemy. 
