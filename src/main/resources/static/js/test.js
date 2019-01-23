JS中使用webSocket
websocket.onmessage = function(event){
    var question = new Array();
    question = event.data.split(";");
    if(question[0]=="1000"){
        alert("一组训练结束");
        $("#ul").css('display','none');//将ul元素隐藏
        $("#question").text("10个单词训练结束");
        $("#next").css('display','block');
        return;
    }
    if(question[0]=="1001"){
        alert("题目打错了，在想一想吧！");
        $("#question").text(question[1]);
        $("#A").text(question[2]);
        $("#B").text(question[3]);
        $("#C").text(question[4]);
        $("#D").text(question[5]);
        return;
    }
    loadQuestion(question);
}
function setMessageInnerHTML(str) {
    var div = document.createElement('div');
    div.innerHTML = str;
    if(!str.search("服务器提示：") || !str.search("WebSocket连接")){
        div.style.color = "red";
    }
    document.body.appendChild(div);

}
