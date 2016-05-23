/**
 * Created by Ratus on 23.05.2016.
 */
function clickME(i) {
    var div = document.getElementById(i);
    div.onclick = function (e) {
        var x = e.clientX, y = e.clientY,
            elementMouseIsOver = document.elementFromPoint(x, y);
        var target = e.target || e.srcElement;
        var lastID = target.id;

        //save div color
        var saveColor = div.style.backgroundColor;

        //set color if we press on div element
        div.style.backgroundColor = "#D93600";

        if(confirm('Delete box with id ' + lastID + ' ?')){
            //Submit form without button
            document.getElementById('deleteID').value = lastID;
            document.getElementById("hiddenForm").submit();
            div.parentNode.removeChild(div);
        }else{
            div.style.backgroundColor = saveColor;
            document.getElementById('deleteID').value = 'id';
        }

    }
}
