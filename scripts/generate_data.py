import sqlite3

database = sqlite3.connect('data.db')
database_cursor = database.cursor()

database_cursor.execute("""CREATE TABLE coordinates
		(x real, y real, z real)
		""")

with open('data.csv', 'wb') as out_file:
	for i in xrange(2160000):
		database_cursor.execute("INSERT INTO coordinates VALUES (1, 2, 3)")
		out_file.write('1, 2, 3\n')

database.commit()

