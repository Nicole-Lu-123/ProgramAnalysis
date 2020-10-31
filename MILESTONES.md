Add entries to this file summarising each project milestone. Don't forget that these need to have been discussed with your TA before the milestone deadline; also, remember to commit and push them by then!

## Milestone 1
Brief description of your planned program analysis and/or visualisation ideas.
Notes of any important changes/feedback from TA discussion
Any planned follow-up tasks or features still to design
 
Planned program analysis:
- Analysing the changes made in the file/codes, at what time, who made the change, which distinct file(s) or the methods in the file changed and the change score(shape and filling need to be discussed).


Visualisation:
- A graph where nodes as classes and line as function calls to show the progress of the whole project based on the date. 


There are two choices of presenting the final visualisation:
- Graph1:
  - The graph of each day contains all the classes as circles and relationships(if exist) as edges. For different days, the graph can be differerent, since classes can be added or deleted when menber changed the program. The changed class will be marked as blue(for example), the rest with no change will be grey. User can choose the date to see which class is changed. If the user also choose some member, the contribute of this(these) memer(s) will be shown as a part of the class circle, like a tiny pie chart, also the contributes will be colored in different colors. 
 
- Graph2:
  - The graph shown on a specific day or of a specific member is a set of circles. The number of circles is determined by the number of changed classes and the radius of the circle is determined by the number of lines changed in the class.(e.g. 5 lines makes the radius is 1, using absolute number for delete lines is pos, lines that changes is regard as added lines)
 

Follow-up tasks:
- dicide which graph to use, and more details of the graph
- discuss with TA

## TA advise:
- Plan1 is suitable since it shows the different classes that's been added or removed and it allows you to extend it by adding additional detail based on what your chosen analysis would be.
- In order to make this idea work, you must somehow present how each code change affects the behaviour of the program (e.g. loops/functions, some property of program behaviour) or some form of analysis that analyzes what program property has changed due to the code change (e.g. coupling/dependency between class, etc..).
