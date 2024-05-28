let select = document.getElementById("select");
let list = document.getElementById("list")
let selectText = document.getElementById("selectText");
let inputfield = document.getElementById("inputfield");

let options = document.getElementsByClassName("options");

// select.onclick = function() {
//     list.classList.toggle("open");
// }

for(option of options) {
    option.click = function() {
    selectText.innerHTML = this.innerHTML;
    }
}