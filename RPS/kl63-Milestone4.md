<table><tr><td> <em>Assignment: </em> IT114 RPS Milestone4</td></tr>
<tr><td> <em>Student: </em> Kevin Lin (kl63)</td></tr>
<tr><td> <em>Generated: </em> 5/1/2023 11:58:35 AM</td></tr>
<tr><td> <em>Grading Link: </em> <a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-006-S23/it114-rps-milestone4/grade/kl63" target="_blank">Grading</a></td></tr></table>
<table><tr><td> <em>Instructions: </em> <p>Implement the features from Milestone4 from the proposal document:&nbsp;&nbsp;<a href="https://docs.google.com/document/d/11SRMo7JkLAMM-PuuiGwl_Z-QXP3pyQ7xN3lRxwmcwCc/view">https://docs.google.com/document/d/11SRMo7JkLAMM-PuuiGwl_Z-QXP3pyQ7xN3lRxwmcwCc/view</a></p>
</td></tr></table>
<table><tr><td> <em>Deliverable 1: </em> Client can mark themselves “away” to be skipped in the turn flow but still be in the game </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot(s) of the visual representation of someone "away"</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/235471664-c7a40d85-1d00-43f3-91c6-ade6f4595fb9.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing away user<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/235477643-5e29cfa8-d364-4215-88f4-04cda6645652.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of code for creating of Away and Spectator button<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/235477906-f8bc9780-bdad-4f49-b510-60768c598a1c.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of code for  sizing and adding of Away and Spectator buttons<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Briefly explain the code logic</td></tr>
<tr><td> <em>Response:</em> <p>When the player joins the game it has the option to mark themselves<br>as away. When the player clicks on away it will notify the other<br>players that the player is away in the user list panel.<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 2: </em> Client can join as spectator </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot(s) of what a spectator can see</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/235479738-7bde4279-dfab-4e2d-8331-a3f3e5fcfd9c.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>UI for the Spectator Mode<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/235477643-5e29cfa8-d364-4215-88f4-04cda6645652.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of code for creating of Away and Spectator button<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/235477906-f8bc9780-bdad-4f49-b510-60768c598a1c.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of code for  sizing and adding of Away and Spectator buttons<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/235480560-ed049f28-c784-48ee-bf65-b4b83c099787.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of numReadyCount is a count of who is ready and made a<br>choice. This way the others aren&#39;t counted for the battle (game logic).<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/235481348-0d014205-6e3c-4706-ac01-2b359fcdefa5.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Code for showing visibility of the button. If the player is a spectator<br>their choice button will not show. Else is not spectator their choice button<br>will show.<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/235481633-0adf5995-91c7-4590-ba3a-62949cd09285.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of the UI showing spectator can&#39;t make a choice. Also marked with<br>a S in the user list panel<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Briefly explain how the code handles spectators</td></tr>
<tr><td> <em>Response:</em> <p>After the player joins the game room they will be given the option<br>to be a spectator. If they choose to be a spectator it will<br>mark them a&nbsp; spectator in the user list panel. Then when 2 players<br>are ready it will start the game but the spectator can&#39;t participate but<br>can see the game flow.<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 3: </em> Implement extra options beyond Rock Paper and Scissors </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707795-a9c94a71-7871-4572-bfae-ad636f8f8474.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot(s) showing the extra options/choices</td></tr>
<tr><td><table><tr><td>Missing Image</td></tr>
<tr><td> <em>Caption:</em> (missing)</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Briefly explain what you added</td></tr>
<tr><td> <em>Response:</em> <p>(missing)</p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 4: </em> Implement a cooldown on an option (i.e., same option can’t be picked twice in a row by the same player) </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707795-a9c94a71-7871-4572-bfae-ad636f8f8474.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot(s) showing the cooldown active where option can't be picked</td></tr>
<tr><td><table><tr><td>Missing Image</td></tr>
<tr><td> <em>Caption:</em> (missing)</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Briefly explain the code logic</td></tr>
<tr><td> <em>Response:</em> <p>(missing)</p><br></td></tr>
</table></td></tr>
<table><tr><td><em>Grading Link: </em><a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-006-S23/it114-rps-milestone4/grade/kl63" target="_blank">Grading</a></td></tr></table>