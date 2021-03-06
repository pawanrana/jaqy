.if
---

``.if`` executes a block of jaqy script if and only if the expression
condition is evaluated to true.

Syntax
~~~~~~

.. code-block:: text

	.if [expression]
	string
	.end if

Note that any SQL must end with ``;`` or the parser would consider that more
text are coming.

The expression is evaluated using JavaScript.

Example
~~~~~~~

.. code-block:: sql

	SELECT * FROM MyTable ORDER BY a;
	-- Check the number of rows returned from a query
	.if activityCount == 0
	.rem Test
	SELECT 2;
	.rem Test
	.end if

See Also
~~~~~~~~

* `.var <var.html>`__
* `.script <script.html>`__
