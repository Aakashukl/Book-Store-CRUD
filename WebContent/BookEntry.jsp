<html>
<body>
	<h3>Book Entry Form</h3>
	<form method="post" action="BookEntry">
		<table>
			<tr>
				<th>Book Code</th>
				<th>
				<input type="text" name="bcode">
				</th>
			</tr>
			<tr>
				<td>Book Name</td>
				<td>
				<input type="text" name="btitle">
				</td>
			</tr>
			<tr>
				<td>Book Author</td>
				<td>
				<input type="text" name="bauthor">
				</td>
			</tr>
			<tr>
				<td>Book Subject</td>
				<td>
				<input type="text" name="bsubject">
				</td>
			</tr>
			<tr>
				<td>Book Price</td>
				<td>
				<input type="text" name="bprice">
				</td>
			</tr>
			<tr>
			<td><input type="submit" name="bookSubmut"></td>
			<a href="SearchBook.jsp">Search Book</a>	
			</tr>
		</table>
	</form>
</body>
</html>