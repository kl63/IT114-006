<table><tr><td> <em>Assignment: </em> IT114 M2 Java-HW</td></tr>
<tr><td> <em>Student: </em> Kevin Lin (kl63)</td></tr>
<tr><td> <em>Generated: </em> 2/1/2023 6:19:38 PM</td></tr>
<tr><td> <em>Grading Link: </em> <a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-006-S23/it114-m2-java-hw/grade/kl63" target="_blank">Grading</a></td></tr></table>
<table><tr><td> <em>Instructions: </em> <p><br></p><p><strong>Template Files</strong>&nbsp;You can find all 3 template files in this gist:&nbsp;<a href="https://gist.github.com/MattToegel/fdd2b37fa79a06ace9dd259ac82728b6">https://gist.github.com/MattToegel/fdd2b37fa79a06ace9dd259ac82728b6</a>&nbsp;<br></p><p>Setup steps:</p><ol><li><code>git checkout main</code></li><li><code>git pull origin main</code></li><li><code>git checkout -b M2-Java-HW</code></li></ol><p>You'll have 3 problems to save for this assignment.</p><p>Each problem you're given a template&nbsp;<strong>Do not edit anything in the template except where the comments tell you to</strong>.</p><p>The templates are done in such a way to make it easier to capture the output in a screenshot.</p><p>You'll copy each template into their own separate .java files, immediately git add, git commit these files (you can do it together) so we can capture the difference/changes between the templates and your additions. This part is required for full credit.</p><p>HW steps:</p><ol><li>Open VS Code at the root of your repository folder</li><li>In VS Code create a new folder/directory called M2</li><li>Create 3 new files in this new M2 folder (Problem1.java, Problem2.java, Problem3.java)</li><li>Paste each template into their respective files</li><li><code>git add .</code></li><li><code>git commit -m "adding template baselines</code></li><li>Do the related work (you may do steps 8 and 9 as often as needed or you can do it all at once at the end)</li><li><code>git add .</code></li><li><code>git commit -m "completed hw"</code></li><li>When you're done push the branch<ol><li><code>git push origin M2-Java-HW</code></li></ol></li><li>Create the Pull Request with <b>main</b>&nbsp;as base and&nbsp;<strong>M2-Java-HW</strong>&nbsp;as compare (don't merge/close it yet)</li><li>Create a new file in the M2 folder in VS Code called m2_submission.md</li><li>Fill out the below deliverable items, save the submission, and copy to markdown</li><li>Paste the markdown into the m2_submission.md</li><li>add/commit/push the md file<ol><li><code>git add m2_submission.md</code></li><li><code>git commit -m "adding submission file"</code></li><li><code>git push origin M2-Java-HW</code></li></ol></li><li>Merge the pull request from step 11</li><li>On your local machine sync the changes<ol><li><code>git checkout main</code></li><li><code>git pull origin main</code></li></ol></li><li>Submit the link to the m2_submission.md file from the main branch to Canvas</li></ol><p><br></p></td></tr></table>
<table><tr><td> <em>Deliverable 1: </em> Problem 1 - Only output Odd values of the Array under "Odds output" </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> 2 Screenshots: Clearly screenshot the output of Problem 1 showing the data and show the code</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/216183143-6d64b967-4819-477b-b5b9-109b82a67e52.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing Problem 1 Code<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/216184069-aa215675-a9bd-45b0-87bd-4bd366e73ad2.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing Problem 1 Output<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Describe how you solved the problem</td></tr>
<tr><td> <em>Response:</em> <p>&nbsp;First I created a for loop which iterates through each of the arrays.<br>Then I use an if statement that takes each number in the array<br>and pull out those that are not divisible by 2. Finally, I print<br>those numbers out.<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 2: </em> Problem 2 - Only output the sum/total of the array values (the number must end in 2 decimal places, if it ends in 1 it must have a 0 at the end) </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> 2 Screenshots: Clearly screenshot the output of Problem 2 showing the data and show the code</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/216184320-35f1fad4-1961-4ab5-89fa-eca2cad8d73c.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing Problem 2 Code<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/216184568-21f1e3fe-082a-4e4d-9c56-2d0fe843d623.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing Problem 2 Output<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Describe how you solved the problem</td></tr>
<tr><td> <em>Response:</em> <p>&nbsp;First I created a for loop which iterates through each of the arrays.<br>Then each number of the array gets added and set to the total.<br>This will just repeat until the entire array is counted.<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 3: </em> Problem 3 - Output the given values as positive under the "Positive Output" message (the data otherwise shouldn't change) </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> 2 Screenshots: Clearly screenshot the output of Problem 3 showing the data and show the code</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/216184894-826d8090-1685-4acf-9e66-de68e6b99742.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing Problem 3 Code<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/216185131-1b39e30f-ec75-4a93-bcce-00ec7cc0a578.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing Problem 3 Output<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Describe how you solved the problem</td></tr>
<tr><td> <em>Response:</em> <p>&nbsp;First I created a for loop which iterates through each of the arrays.<br>Then I used an if else if statements to separate different data types<br>of arrays. Then I properly handle the data types by casting. The String<br>had to be converted to an Integer first so it can take the<br>absolute value. after it got converted back to a String. While the Integer<br>and Double were just using the Math.abs methods to convert each number in<br>the array from a negative to a positive.<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 4: </em> Misc Items </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Pull Request URL for M2-Java-HW to main</td></tr>
<tr><td> <a rel="noreferrer noopener" target="_blank" href="https://github.com/kl63/IT114-006/pull/4">https://github.com/kl63/IT114-006/pull/4</a> </td></tr>
<tr><td> <em>Sub-Task 2: </em> Talk about what you learned, any issues you had, how you resolve them</td></tr>
<tr><td> <em>Response:</em> <p>I have learned how to use different types of loops, if statements, and<br>also how arrays work. Aldo, I have learned how to deal with different<br>data types and casting.<br></p><br></td></tr>
</table></td></tr>
<table><tr><td><em>Grading Link: </em><a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-006-S23/it114-m2-java-hw/grade/kl63" target="_blank">Grading</a></td></tr></table>