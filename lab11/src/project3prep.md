# Project 3 Prep

**What distinguishes a hallway from a room? How are they similar?**

Answer: hallway much more like a long rectangular which only allow your avatar to walk， and its width requirement is at least one tensile.
while the room can be any shape that allow you to do things inside and its width requirement is longer than hallway, there are similar in allow avatar to move but room clearly has more things to interact with.

**Can you think of an analogy between the process of 
drawing a knight world and randomly generating a world 
using rooms and hallways?**

Answer: It's much more like place all the parts together to form a world with same element or different element.
i mean like a knight world is assembling all repeating part together (each part is a 5x5 block with a specific pattern)
while a generating a world is assembling different parts together logically, and set a hallway to connecting them of through them.
kinda like lego, knight world is like using the same bricks to construct, and randomly generating a world is using different shape and colors brick to construct.

**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually 
get to the full knight world.**

Answer: Find parts that repeat regularly. for example, i can set types of rooms to generate with special sequence of seed.
but creating a world, is much more like combine different section together, and the hallway is unique element connecting them.

**This question is open-ended. Write some classes 
and methods that might be useful for Project 3. Think 
about what helper methods and classes you used for the lab!**

Answer: A Block creator, for creating parts that repeat frequently,
        A Pattern creator, for creating pattern in the block.
        A seed decider for deciding different sequence of block we used to generate the world, and the way hallway to connecting them.

        
