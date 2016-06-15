function menuinit(){
 document.getElementById("sub1").style.display="none"; 
 document.getElementById("sub2").style.display="none"; 
}
function subMenuOpenBox(menu, subMenu) {
 document.getElementById(subMenu).style.display="";
 document.getElementById(menu).style.backgroundColor='black';
 document.getElementById(menu).style.color='white';
}
/* 서브메뉴 닫기 */
function subMenuCloseBox(menu, subMenu) {
 document.getElementById(subMenu).style.display="none";
 document.getElementById(menu).style.backgroundColor='white';
 document.getElementById(menu).style.color='black';
}

var s1l1 = document.querySelector("#s1l1");
var s1l2 = document.querySelector("#s1l2");
var s2l1 = document.querySelector("#s2l1");
var s2l2 = document.querySelector("#s2l2");
s1l1.addEventListener("mouseover", function textBold() {s1l1.style.color="black";})
s1l1.addEventListener("mouseout", function textBold() {s1l1.style.color="gray";})
s1l2.addEventListener("mouseover", function textBold() {s1l2.style.color="black";})
s1l2.addEventListener("mouseout", function textBold() {s1l2.style.color="gray";})
s2l1.addEventListener("mouseover", function textBold() {s2l1.style.color="black";})
s2l1.addEventListener("mouseout", function textBold() {s2l1.style.color="gray";})
s2l2.addEventListener("mouseover", function textBold() {s2l2.style.color="black";})
s2l2.addEventListener("mouseout", function textBold() {s2l2.style.color="gray";})

function mapOver() {
 this.style.backgroundColor='white';
 this.style.color='black';
}


