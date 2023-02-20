<table><tr><td> <em>Assignment: </em> It114 Milestone1</td></tr>
<tr><td> <em>Student: </em> Kevin Lin (kl63)</td></tr>
<tr><td> <em>Generated: </em> 2/20/2023 5:44:19 PM</td></tr>
<tr><td> <em>Grading Link: </em> <a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-006-S23/it114-milestone1/grade/kl63" target="_blank">Grading</a></td></tr></table>
<table><tr><td> <em>Instructions: </em> <ol><li>Create a new branch called Milestone1</li><li>At the root of your repository create a folder called Project</li><ol><li>You will be updating this folder with new code as you do milestones</li><li>You won't be creating separate folders for milestones; milestones are just branches</li></ol><li>Create a milestone1.md file inside the Project folder</li><li>Git add/commit/push it to Github</li><li>Create a pull request from Milestone1 to main (don't complete/merge it yet)</li><li>Copy in the latest Socket sample code from the most recent Socket Part example of the lessons</li><ol><li>Recommended Part 5 (clients should be having names at this point and not ids)</li><li><a href="https://github.com/MattToegel/IT114/tree/Module5/Module5">https://github.com/MattToegel/IT114/tree/Module5/Module5</a>&nbsp;<br></li></ol><li>Git add/commit the baseline</li><li>Ensure the sample is working and fill in the below deliverables</li><li>Get the markdown content or the file and paste it into the milestone1.md file or replace the file with the downloaded version</li><li>Git add/commit/push all changes</li><li>Complete the pull request merge from step 5</li><li>Locally checkout main</li><li>git pull origin main</li></ol></td></tr></table>
<table><tr><td> <em>Deliverable 1: </em> Startup </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot showing your server being started and running</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/220205454-1d53b99b-fc9c-4930-956b-7cc118aa4c13.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of the Server and Clients running<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add screenshot showing your client being started and running</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/220205454-1d53b99b-fc9c-4930-956b-7cc118aa4c13.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of the Server and Clients running<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain the connection process</td></tr>
<tr><td> <em>Response:</em> <p>The server-side connections work through the Server.java file which then creates a thread<br>using the SeverThread.java file. Then once the server is run it would listen<br>for clients at port 3000. In contrast, the client-side connection works through the<br>Client.java file and needs the severThread.java file in order to communicate with the<br>server using port 3000. The sockets steps are first the server is running<br>and waits for a client to join. Then once a client is joined<br>it would then exchange information. Lastly, when client types disconnected it will disconnect<br>them from the server.<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 2: </em> Sending/Receiving </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot(s) showing evidence related to the checklist</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/220205690-31613fcf-f63b-4a63-ad3b-5cdf8802e6ac.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of Clients talking<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Briefly explain how the messages are sent, broadcasted, and received</td></tr>
<tr><td> <em>Response:</em> <p>On the client side, it&#39;s basically like a messaging app. Where you just<br>type something and press send. Also, you can see others&#39; messages. Just like<br>a messaging app, you can also see who sent what if your receiving<br>it. Third, the ServerThread uses the method sendMessage() to send out the payload<br>message. Also for Room, I see it as a spectator who can see<br>everything going on but only in that room.&nbsp;<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 3: </em> Disconnecting / Terminating </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot(s) showing evidence related to the checklist</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/220206202-84986cc8-0dc4-415f-8ab1-0965dc6a4fe5.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>Screenshot of Clients disconnecting <br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/113387088/220207903-1bc6bb4b-1b2b-412f-825c-af46dda4fadd.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>disconnecting and terminating the server<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Briefly explain how the various disconnects/terminations are handled</td></tr>
<tr><td> <em>Response:</em> <p>From the Socket&#39;s perceptive once a Client disconnects you will see the socket<br>closing and will start the cleanup process. also it will notify the Client<br>regarding the disconnection. Secondly, the Clients won&#39;t crash because we have a built-in<br>boolean that detects whether it still running or not. (isRunning). Lastly, the Server<br>won&#39;t crash because once it&#39;s disconnected it will start the clean up process<br>with the cleanup() method in SeverThread.java. So that method will run once disconnected.<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 4: </em> Misc </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add the pull request for this branch</td></tr>
<tr><td> <a rel="noreferrer noopener" target="_blank" href="https://github.com/kl63/IT114-006/pull/9">https://github.com/kl63/IT114-006/pull/9</a> </td></tr>
<tr><td> <em>Sub-Task 2: </em> Talk about any issues or learnings during this assignment</td></tr>
<tr><td> <em>Response:</em> <p>Watching the video lecture really helped me get a deeper understanding of all<br>the files and what each method does. I say I have gotten better<br>at understanding the process from the last homework to now.<br></p><br></td></tr>
</table></td></tr>
<table><tr><td><em>Grading Link: </em><a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-006-S23/it114-milestone1/grade/kl63" target="_blank">Grading</a></td></tr></table>