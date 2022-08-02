"# megafon_test_task" 
Условие задания
Есть произвольный поток числовых данных - аргументов. Аргументы приходят фиксированным набором с произвольными значениями. Имя аргумента фиксировано.
Есть справочник формул (например, в текстовом файле). В каждой формуле может использоваться часть приходящих аргументов. 
Аргументы в формулах и приходящих наборах связываются по имени.
Необходимо написать консольное приложение для расчета значений по каждой из формул справочника для каждого приходящего набора аргументов. 
Справочник может меняться во время выполнения программы. Измененные формулы должны попадать в обработку без остановки выполнения приложения. 
Предполагается большая нагрузка, поэтому парсить формулы для каждого набора данных нельзя. 
Необходимо обеспечить компиляцию формул только при старте приложения и по событию (например, при изменении справочника).
Поток наборов аргументов можно обеспечить непрерывным чтением из файла в цикле или непрерывной генерацией случайных чисел.
