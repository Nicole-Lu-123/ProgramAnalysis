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

TA advise:
- Plan1 is suitable since it shows the different classes that's been added or removed and it allows you to extend it by adding additional detail based on what your chosen analysis would be.
- In order to make this idea work, you must somehow present how each code change affects the behaviour of the program (e.g. loops/functions, some property of program behaviour) or some form of analysis that analyzes what program property has changed due to the code change (e.g. coupling/dependency between class, etc..).



## Milestone 2
- Planned division of main responsibilities between team members

  - Nicole: Deal with analysis (feature, method, dependency,inherited,extend,comparison between two commit)
  - Tian: Deal with analysis (same as above)
  - Sunny:  visualizations
  - Difei:  visualizations



- Roadmap for what should be done 

  - Design program analyses to extract information
  - Write the user examples
  - Complete the program analyses
  - Implement the visualizations (html+ css or library)
  - Debug
  - Evaluate the analyses / visualizations using empirical studies 
 

- Summary of progress so far
  - We have contacted the TA and we have changed idea so far. Now, it is in the right track. 
  - Here is our new idea:
   - For analysis part: we planned to do static analysis (feature, method, dependency) and dynamic analysis (check for loop, function call, etc). And nodes in graph will represent the class, with functions name and feature inside nodes. The line between two nodes will indicate the dependency. If there is on dependency between two classes after changing the code or classes, the line would be disappear. Number of function calls and for loop would also besides the nodes. Then, we will use library like D3.js to draw the graph. (may be use java 3D to visualize as a 3D graph, but just an idea)




## Milestone 3
- Visualization idea:
 - Planet - class, size of planet stands for the size of the class file
 - trees on planet - fields
 - animals on planet - methods
- Spaceships between planets - represents dependency  (including inheritance between classes, function calls)
- Satellite around the planet - a satellite represents a for loop, for-loop with higher iterations has higher speed



- Our target users are project managers or stduents with very little programming background and who want to have a visual overview of each commit and code changes to relationship intra and inter-classes. 


Experiment Student: Arts Student with litmited programming knowledge 


In first user study, 


- Stage one: 
the target user was provided with an image visualization of commit 1 and he/she was asked to review all the components and provide feedback. 


- Feedback: 
she was able to undertand the relationship between classes and how different components and attributes existed within a class. she found it very informative to have an overview of a commit. 

Image:https://drive.google.com/file/d/12kCwaq47zR0soiaKKk_Yk4m7XNO4Rk9f/view


- Stage two:
the target user was offered another image of commit 2 for comparision in this case to check for code changes between commit. 


- Feedback: 
she was able to see the difference between commit 1 and commit 2 without any further clarifications. She was able to distinguish and identify differences in code chagnes within a class and relationship changes between classes. In addition, she was able to deliver with the use of terminolgy effectively. she found such visualization can enhance the understanding of each commit and she was able to grasp the idea of code changes much more faster. 
Image:https://drive.google.com/file/d/15tIO-saKIveVJ4YKR3hiWLxMiGzSoBhs/view

After consideration, we will not make any changes to the original language design. 



## Milestone 4
- Status of implementation


We always got refreshed ideas after the group meeting.  Realizing the analysis between two commits puts a lot of pressure on us since we just started our work.

- Here are some extra changes:


For the analysis part, we would find out which method has for-loops in the function body. And finally shows the all the methods with different colors which represents if the number of the for-loops, for example, if there is no for-loop, the color of object representing as method is white, if there are 2 for-loops then the color is grey. 


- Plans for final user study


We planned to conduct our final user study to another 2 person after the implementation is done. They better have coding experience.


- Planned timeline for the remaining days
  - Nov 21st - Nov 27st: Parse the data, Static (all class name, all method name, all method which contain loops),dependency (inherited class; extend class; A class contains B class..); All comparison between two commit. Hope to do Dynamic Analysis ---- (Nicole and Yuxin)
  - Nov 21nd - Nov 27th: Visualization (Used the stored data) --- (Sunny and Difei)
  - Nov 27th - Nov 28th: Final user study, Improve the implementation and Test
  - Nov 28th: Preparation of the video, like writing script
  - Nov 29th: Make the video
  
  
- Visualization:
  - Sphere: (class)
  - blue sphere - class in the first commit
  - blue-green sphere - class in the second commit

  - Cube: (method)
  - methods in the same class have same color

 
  - Line: (dependency)
  - green - extends
  - white - implements
  - blue - function calls in other classes
 


-User study1
  -The user thinks our 3D visualization is awesome, especially the shined items and zoom in/out operations.  After explaining, the user knows what the different lines represent and cubes are methods, he thinks the program can show the changes between two commits including classes, methods and the relationships between classes which is good. Althogh the idea to show these is novel,  the information showed is not really rich and acceptable, it will be better if there is a text to show the information about classes, including methods names, add/delete methods and the number of changed methods. 

-User study2
  -The user can understand the visualization after we explained about how the objects in the java3D represents the project. 
  -It hard to observe the changes if the object is out of scene.(Change: we add the functional key listenser to allow the user to c    ontrol the viewpoint of the final scene by using four arrow keys: "up" "down" "left" and "right")
  -The information is not so clear. (Change:  When the user click on the sphere, the window will present all the class information we  analysed on the pop-up window.)
