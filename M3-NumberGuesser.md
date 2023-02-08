<table><tr><td> <em>Assignment: </em> IT114 - Number Guesser</td></tr>
<tr><td> <em>Student: </em> Kevin Lin (kl63)</td></tr>
<tr><td> <em>Generated: </em> 2/8/2023 3:24:27 PM</td></tr>
<tr><td> <em>Grading Link: </em> <a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-006-S23/it114-number-guesser/grade/kl63" target="_blank">Grading</a></td></tr></table>
<table><tr><td> <em>Instructions: </em> <ol><li>Create the below branch name</li><li>Implement the NumberGuess4 example from the lesson/slides</li><li>Add/commit the files as-is from the lesson material (this is the base template)</li><li>Pick two (2) of the following options to implement</li><ol><li>Display higher or lower as a hint after a wrong guess</li><li>Implement anti-data tampering of the save file data (reject user direct edits)</li><li>Add a difficulty selector that adjusts the max strikes per level</li><li>Display a cold, warm, hot indicator based on how close to the correct value the guess is (example, 10 numbers away is cold, 5 numbers away is warm, 2 numbers away is hot; adjust these per your preference)</li><li>Add a hint command that can be used once per level and only after 2 strikes have been used that reduces the range around the correct number (i.e., number is 5 and range is initially 1-15, new range could be 3-8 as a hint)</li><li>Implement separate save files based on a "What's your name?" prompt at the start of the game</li></ol><li>Fill in the below deliverables</li><li>Create an m3_submission.md file and fill in the markdown from this tool when you're done</li><li>Git add/commit/push your changes to the HW branch</li><li>Create a pull request to main</li><li>Complete the pull request</li><li>Grab the link to the m3_submission.md from the main branch and submit that direct link to github</li></ol></td></tr></table>
<table><tr><td> <em>Deliverable 1: </em> Implementation 1 (one of the picked items) </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Two Screenshots: Add a screenshot demonstrating the feature during runtime; Add a screenshot (or so) of the snippets of code that implement the feature</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/217631510-55adea8b-8467-4297-8d6b-f75b56f0b997.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of Hint 1 Code<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/217631806-fadd2284-1250-48d0-805b-d9377632d872.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of Hint 1 Terminal<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Briefly explain the logic behind your implementation</td></tr>
<tr><td> <em>Response:</em> <p>So I first create an integer variable that calculates the difference between the<br>range from the correct number and the user-guessed number. Then I used an<br>if-else statement that prints weather the guess is higher or lower than the<br>correct number and prints out the appropriate output.<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 2: </em> Implementation 2 (one of the picked items) </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Two Screenshots: Add a screenshot demonstrating the feature during runtime; Add a screenshot (or so) of the snippets of code that implement the feature</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/217632017-a47312e6-5438-41b7-8cf6-16f77bc97289.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of Hint 2 Code<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/217631806-fadd2284-1250-48d0-805b-d9377632d872.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of Hint 2 Terminal<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Briefly explain the logic behind your implementation</td></tr>
<tr><td> <em>Response:</em> <p>Here I used the same integer variable created in the first hint and<br>if the range is less than or equal to 2 and not equal<br>to 0 it will print HOT and if the range is between 2-5<br>and not equal to 0 it will print WARM. Third, if the range<br>is between 5-10 and not equal to 0 it will print COLD. Lastly,<br>anything greater than or equals to&nbsp; 11 and not equal to 0 is<br>going to be VERY COLD.<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 3: </em> Misc </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add a link to the related pull request of this hw</td></tr>
<tr><td> <a rel="noreferrer noopener" target="_blank" href="https://github.com/kl63/IT114-006/pull/7">https://github.com/kl63/IT114-006/pull/7</a> </td></tr>
<tr><td> <em>Sub-Task 2: </em> Discuss anything you learned during this lesson/hw or any struggles you had</td></tr>
<tr><td> <em>Response:</em> <p>I learned to read the existing code and find the correct spot to<br>fit my code to implement the hints.&nbsp;<br></p><br></td></tr>
</table></td></tr>
<table><tr><td><em>Grading Link: </em><a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-006-S23/it114-number-guesser/grade/kl63" target="_blank">Grading</a></td></tr></table>