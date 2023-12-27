<!DOCTYPE html>

<html lang="en">

    <head>

        <meta charset="utf-8"/>
        <title>Reminder</title>

    </head>

    <body>

        <header>
            <nav>
                <h1>Reminder</h1>
            </nav>
        </header>
        <main>
            <h2>You have created a reminder.</h2>
            <p><a href="${server}/reminder/confirm/${token}">Confirm</a> your reminder | <a href="${server}/reminder/cancel/${token}">Cancel</a> your reminder.</p>
        </main>

        <footer>
            <p>&copy; 2024 Jerry Kiely</p>
        </footer>

    </body>

</html>
