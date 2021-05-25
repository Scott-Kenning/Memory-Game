# Memory-Game
A tool to memorize anything you want




Memory Game is a game meant to help memorize whatever you choose, whether it be times tables or biology notes.

To use the program, simply input each question you would like to be tested on in the file "items.txt". The 
formatting should be as follows:

"answer";"description"

For example, if you were trying to learn the multiplication table you would put:

108;nine times twelve

It should be noted that the description should not be a full question, the description label can only show
one line of text

IMPORTANT: The answer is taken as the string of everything before the semi colon and the description is everything
after the semi colon. Answers must be typed exactly as they are written, any extra spaces will be recorded as
part of the description/answer. In this example

108 ; five times five

the question would only be considered correct if the user inputted "108 "

-------------------------------------------------------------------------------------------------------------------

Instructions



Classic mode:

In this mode, all items are added to the list of questions. Whenever you guess correctly, the item you guessed is
removed from the list. When you get a question wrong, the item is not removed, and you instead are given a new question.
This means that a question you got wrong will show up again. You also have the option to reveal the answer to a question.
If you do this, the item is removed from the question list and added to a list of items to be reviewed. Once all items
have been guessed correctly or their answers have been revealed, the game is over and you are given a score. Clicking the
review button will restart the game with only the items that had answers revealed the previous round. This process repeats
until all questions have been guessed correctly.


Streak mode:

The object of this game mode is to get as many questions right in a row. All the items from the document are added to  the
question list. When an item is guessed correctly, it is NOT removed from the list, meaning you can get the same question
twice. The game ends when an incorrect answer is entered.


Timed mode:

The object of this game mode is to get as many questions right in one minute. The timer starts the second the game mode is
chosen, and there is no penalty for right or wrong answers. At the end of the minute your score is presented, but there is
currently no highscore system. 
