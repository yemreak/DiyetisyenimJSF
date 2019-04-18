/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * 
 * @author YEmre
 */

var log_button = document.getElementById('log-button')
log_button.style.display = 'none';
document.getElementById('reg-button').style.display = 'none';

function toggle_visibility(id){
    var e = document.getElementById(id);
    if (e.style.display === 'block')
        e.style.display = 'none';
    else 
        e.style.display = 'block';
}

function toggle_log_reg_screen(id){
    var otherId;
    
    if (id === 'log-screen') {
        otherId = 'reg-screen';
    } else if (id === 'reg-screen') {
        otherId = 'log-screen';
    }
    
    makeVisible(id);
    makeInvisible(otherId);
}

function makeVisible(id){
   document.getElementById(id).style.display = 'block';
}

function makeInvisible(id){
    document.getElementById(id).style.display = 'none';
}


