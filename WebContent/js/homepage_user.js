var c = document.querySelectorAll(".myCanvas");
for(let i=0; i<c.length; i++){
  var ctx = c[i].getContext("2d");
  r = c[i].dataset.rating;
  for(j=0;j<5;j++){
    k = r - j;
    if(k<0) k=0;
    if(k>1) k=1;
    star(ctx,10+(j*20),10,9,5,0.5,k);
  }   
}
function star(ctx, x, y, r, p, m, k)
{
    ctx.save();
    ctx.beginPath(); 
    ctx.translate(x, y);
    ctx.moveTo(0,0-r);
    for (var i = 0; i < p; i++)
    {
        ctx.rotate(Math.PI / p);
        ctx.lineTo(0, 0 - (r*m));
        ctx.rotate(Math.PI / p);
        ctx.lineTo(0, 0 - r);
    }
 if(k==0) ctx.fillStyle = 'black';
 if(k==1) ctx.fillStyle = 'gold';
 if(k>0 && k<1){
   var my_gradient = ctx.createLinearGradient(0,0,1,0);
my_gradient.addColorStop(0, "gold");
my_gradient.addColorStop(1, "black");
ctx.fillStyle = my_gradient;
 }
    ctx.fill();
    ctx.restore();
}




