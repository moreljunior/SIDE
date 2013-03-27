licence=$1
chemin=$2

oldIFS=$IFS     
IFS=$'\n'    

var=`cat $licence`


# avoid webscript js files
for f in `find $chemin -type f -and \( -name "*.java" -or -name "*.js" \) -and ! \( -path "*/resources/alfresco/*" -or -path "*web-extension*" \) -and -path "*com.bluexml*" `; do
	filein=$f
	num=1
	perl -pi -le 'print "/*" if $. == "'$num'"' $filein
	num=$(($num+1))
	for i in $var; do
        	perl -pi -le 'print "'$i'" if $. == "'$num'"' $filein
		num=$(($num+1))
	done

	perl -pi -le 'print "*/" if $. == "'$num'"' $filein
done

for f in `find $chemin -type f -name "*.xml" `; do
	filein=$f
	num=2
	perl -pi -le 'print "<!--" if $. == "'$num'"' $filein
	num=$(($num+1))
	for i in $var; do
        	perl -pi -le 'print "'$i'" if $. == "'$num'"' $filein
		num=$(($num+1))
	done

	perl -pi -le 'print "-->" if $. == "'$num'"' $filein
done

for f in `find $chemin -type f -name "*.ecore" -o -name "*.ecore_diagram" -o -name "*.ecorediag" -o -name "*.genmodel" -o -name "*.chain" -o -name "*.exsd" `; do
	filein=$f
	num=1
	perl -pi -le 'print "<!--" if $. == "'$num'"' $filein
	num=$(($num+1))
	for i in $var; do
        	perl -pi -le 'print "'$i'" if $. == "'$num'"' $filein
		num=$(($num+1))
	done

	perl -pi -le 'print "-->" if $. == "'$num'"' $filein
done

for f in `find $chemin -type f -name "*.properties"`; do
	filein=$f
	num=1
	for i in $var; do
        	perl -pi -le 'print "#  '$i'" if $. == "'$num'"' $filein
		num=$(($num+1))
	done

done

for f in `find $chemin -type f -name "*.mt" `; do
	filein=$f
	num=1
	perl -pi -le 'print "<%--" if $. == "'$num'"' $filein
	num=$(($num+1))
	for i in $var; do
        	perl -pi -le 'print "'$i'" if $. == "'$num'"' $filein
		num=$(($num+1))
	done

	perl -pi -le 'print "--%>" if $. == "'$num'"' $filein
done

IFS=$old_IFS
