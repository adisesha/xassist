# Introduction #
If u r tired of downloading plugins to make your xml editor provide content assist, this could be your project. Instead of the current trend of having the heavy xml editor which is then enhanced by heavier plugins for spring, faces and everything else, this editor is a light editor with a preferences based assist model.

## Some More Details ##
The design is to have a very simple editor. For content assist we have proposals just like code templates which is outlined in an xml file. For example we could have a simple rule that says for any xml file that starts with "beans" as its root tag, we should provide class name as the content assist for the attribute "class" in element bean. Something like
/beans/bean@class = ${package\_name}.${type\_name}

This could be exported and imported from an xml file which would look something like



&lt;templates&gt;


> 

&lt;template autoinsert="true" path="/beans/bean@class" description="For all class attributes in bean use classnames"&gt;


> > ${package\_name}.${type\_name}

> 

&lt;/template&gt;




&lt;/templates&gt;


