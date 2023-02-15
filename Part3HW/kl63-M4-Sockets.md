<table><tr><td> <em>Assignment: </em> IT114 - Sockets Part 1 - 3</td></tr>
<tr><td> <em>Student: </em> Kevin Lin (kl63)</td></tr>
<tr><td> <em>Generated: </em> 2/15/2023 5:12:36 PM</td></tr>
<tr><td> <em>Grading Link: </em> <a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-006-S23/it114-sockets-part-1-3/grade/kl63" target="_blank">Grading</a></td></tr></table>
<table><tr><td> <em>Instructions: </em> <ol><li>Create a new branch for this assignment</li><li>Go through the socket lessons and get each part implemented (parts 1-3)</li><ol><li>You'll probably want to put them into their own separate folders/packages (i.e., Part1, Part2, Part3) These are for your reference</li><li>Part 3, below, is what's necessary for this HW</li><li><a href="https://github.com/MattToegel/IT114/tree/Module4/Module4/Part3">https://github.com/MattToegel/IT114/tree/Module4/Module4/Part3</a><br></li></ol><li>Create a new folder called Part3HW</li><li>Create an empty m4_submission.md file in Part3HW (or skip this step and download the file at the end)</li><ol><li>Add/commit/push the branch</li><li>Create a pull request to main and keep it open</li></ol><li>Copy the the Part3 code into this new folder (Part3HW)</li><li>Git add/commit all of the sample code and the Part3HW code</li><li>Implement <b>two </b>of the following <b>server-side</b> activities for all connected clients (majority of the logic should be processed server-side and broadcasted/sent to all clients if/when applicable)</li><ol><li>Simple number guesser where all clients can attempt to guess while the game is active</li><ol><li>Have a start command that activates the game allowing guesses to be interpreted</li><li>Have a stop command that deactivates the game, guesses will be treated as regular messages</li><li>Have a guess command that include a value that is processed to see if it matches the hidden number (i.e., <i>guess 5</i>)</li><ol><li>Guess should only be considered when the game is active</li><li>The response should include who guessed, what they guessed, and whether or not it was correct (i.e., Bob guessed 5 but it was not correct)</li></ol><li>No need to implement complexities like strikes</li></ol><li>Coin toss command (random heads or tails)</li><ol><li>Command should be something logical like flip or toss or coin or similar</li><li>The result should mention <i>who</i>&nbsp;did <i>what</i>&nbsp;and got what <i>result</i>&nbsp;(i.e., Bob Flipped a coin and got heads)</li></ol><li>Dice roller given a command and text format of "roll #d#" (i.e., roll 2d6)</li><ol><li>Command should be in the format of roll #d# (i.e., roll 1d10)</li><li>The result should mention&nbsp;<i>who</i>&nbsp;did&nbsp;<i>what</i>&nbsp;and got what&nbsp;<i>result</i>&nbsp;(i.e., Bob rolled 1d10 and got 7)</li></ol><li>Math game (server outputs a basic equation, first person to guess it correctly gets congratulated and a new equation is given)</li><ol><li>Have a start command that activates the game allowing equaiton to be answered</li><li>Have a stop command that deactivates the game, answers will be treated as regular messages</li><li>Have an answer command that include a value that is processed to see if it matches the hidden number (i.e.,&nbsp;<i>answer 15</i>)<br></li><ol><li>The response should include who answered, what they answered, and whether or not it was correct (i.e., Bob answered 5 but it was not correct)</li></ol></ol><li>Private message (a client can send a message targetting another client where only the two can see the messages)</li><ol><li>Command can be pm, dm or an @ preceding the users name (clearly note which)</li><li>The server should properly check the target audience and send the response to the original sender and to the receiver</li><li>Alternatively (make note if you do this and show evidence) you can add support to private message multiple people at once. Evidence should show a larger number of clients than the target list of the private message to show it works. Note to grader: if this is accomplished add 0.5 to total final grade on Canvas.</li></ol><li>Message shuffler (randomizes the order of the characters of the given message)</li><ol><li>Command should be shuffle or randomize (clearly mention what you chose)</li><li>The message should be sent to all clients showing it's from the user but randomized</li><ol><li>Example: Bob types <i>command</i>&nbsp;hello and everyone recevies Bob: lleho</li></ol></ol></ol><li>Fill in the below deliverables</li><li>Save and generated the markdown or markdown file</li><li>Update the m4_submission.md file in the Part3HW folder</li><li>Add/commit/push your changes</li><li>Merge the pull request</li><li>From the M4-Sockets branch, navigate to your m4_submission.md file on github and copy the link</li><li>Submit the direct link to Canvas</li></ol></td></tr></table>
<table><tr><td> <em>Deliverable 1: </em> Baseline </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add as many screenshots as necessary to show basic communication among 3 clients and 1 server</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/219177532-baee48c2-4c47-4737-9860-42f4536e3593.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of Terminal #1<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/219178106-ffe52c88-7ed4-4c02-963a-8f98c2745a71.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of Terminal #2<br></p>
</td></tr>
</table></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 2: </em> Feature Implementation 1 </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> What feature did you pick? Briefly explain how you implemented it</td></tr>
<tr><td> <em>Response:</em> <p>Feature:&nbsp;<ol><li>Simple number guesser where all clients can attempt to guess while the game<br>is active</li></ol><div>Comments:</div><div>I created a string function Number and used the random method to<br>generate the random numbers 1-10. Then I return the number.</div><br></p><br></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add screenshot(s) showing the implemented feature working</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/219177532-baee48c2-4c47-4737-9860-42f4536e3593.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of Terminal #1<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/219178106-ffe52c88-7ed4-4c02-963a-8f98c2745a71.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of Terminal #2<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Add screenshot(s) of related code changes to highlight the new logic</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/219175200-e61ed164-c87f-475e-97db-66332ca90da0.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of processComand().<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/219180596-01764595-66f9-471c-b185-368d0ed9dd18.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of Number Guesser<br></p>
</td></tr>
</table></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 3: </em> Feature Implementation 2 </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> What feature did you pick? Briefly explain how you implemented it</td></tr>
<tr><td> <em>Response:</em> <p>Feature:&nbsp;<ol><li>Coin toss command (random heads or tails)<br></li></ol><div>Comments:</div><div>I created a string function Flipper and<br>used the random method to generate the random numbers 0-1. Then used an<br>if else statement to determine whether its Heads or Tails. Lastly, I return<br>the output.</div><br></p><br></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add screenshot(s) showing the implemented feature working</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/219177532-baee48c2-4c47-4737-9860-42f4536e3593.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of Terminal #1<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/219178106-ffe52c88-7ed4-4c02-963a-8f98c2745a71.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of Terminal #2<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Add screenshot(s) of related code changes to highlight the new logic</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/219175200-e61ed164-c87f-475e-97db-66332ca90da0.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of Coin Flip function<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/219175200-e61ed164-c87f-475e-97db-66332ca90da0.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of processComand().<br></p>
</td></tr>
</table></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 4: </em> Misc </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Did you have an issues and how did you resolve them? If no issues, what did you learn during this assignment that you found interesting?</td></tr>
<tr><td> <em>Response:</em> <p>Got stuck on relaying the message to all clients. I used the broadcast<br>function to solve the issue.&nbsp;<br></p><br></td></tr>
<tr><td> <em>Sub-Task 2: </em> Pull request link</td></tr>
<tr><td> <a rel="noreferrer noopener" target="_blank" href="https://github.com/kl63/IT114-006/pull/8/">https://github.com/kl63/IT114-006/pull/8/</a> </td></tr>
</table></td></tr>
<table><tr><td><em>Grading Link: </em><a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-006-S23/it114-sockets-part-1-3/grade/kl63" target="_blank">Grading</a></td></tr></table>