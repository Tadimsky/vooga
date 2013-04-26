You should implement two classes here: VisitMethods.java and *MyGame*Collisions.java 

VisitMethods.java is the place where you want to write all your visit methods and handle all 
your collision logic. If you need to know the direction for some of your collisions, feel free 
to create an instance of CollisionDirection - a tool that will tell you the direction of any collision. 

*MyGame*Collisions.java is the place where you can dump your helper functions. If you check out 
MarioCollisions.java you can see a function there for marioAndPlatform collisions. This method could 
really just be handled in VisitMethods.java under the visit method between IPlayer/IPlatform. Therefore, 
you don't HAVE to implement a *MyGame*Collisions class. It just helps with code organization. If you want 
your visit methods to be super clean, this would be the way to go. Basically, if you have defined a 
generic collision such as one between IPlayer/IPlatform, it would be nice to put it into your 
*MyGame*Collisions class. However, if you have a specific collision between Mario/Shell, then you 
may want to just dump the logic for that in the visit method in VisitMethods.java.   