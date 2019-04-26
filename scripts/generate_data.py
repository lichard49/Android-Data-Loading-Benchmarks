import sqlite3

database = sqlite3.connect('data.db')
database_cursor = database.cursor()

database_cursor.execute("""CREATE TABLE coordinates
		(x real, y real, z real)
		""")

for i in xrange(1000):
	database_cursor.execute("INSERT INTO coordinates VALUES (1, 2, 3)")

database.commit()

