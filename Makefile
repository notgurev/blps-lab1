all:
	@echo hi

helios:
	ssh -p2222 -Y -L 5432:pg:5432 s286528@se.ifmo.ru