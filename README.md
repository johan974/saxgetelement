#  Functionality

Get the data of the enclosing XML element that is found via a String array with path elements

# Technical stuff

Use the SAX parser (rather than DOM)for more efficient memory usage. 

# Usage

getElementDataByPath( { "a", "b", "c#3", "d#2")

That is: find

<pre>
&lt;a>
  &lt;b>
    &lt;c>
      &lt;d>
      ... 
      &lt;/>
    &lt;/c>
    &lt;c>
      ... 
    &lt;/c>
    &lt;c>
      &lt;d>
      ... 
      &lt;/>
      &lt;d>
      This is the text you are looking for! 
      &lt;/>
    &lt;/c>

</pre>