function getCart(){return JSON.parse(localStorage.getItem('obbattuCart')||'[]')}
function saveCart(c){localStorage.setItem('obbattuCart',JSON.stringify(c));updateCount()}
function addToCart(name,price){let c=getCart(),i=c.findIndex(x=>x.name===name);if(i>=0)c[i].qty++;else c.push({name,price,qty:1});saveCart(c);alert(name+' added to cart!')}
function updateCount(){let n=getCart().reduce((s,x)=>s+x.qty,0);document.querySelectorAll('#cartCount').forEach(e=>e.textContent=n)}
function renderCart(){let c=getCart(),box=document.getElementById('cartItems'),total=0;if(!c.length){box.innerHTML='<p>Your cart is empty. <a href="shop.html"><b>Shop now →</b></a></p>';document.getElementById('total').textContent='0';return}box.innerHTML=c.map((x,i)=>{total+=x.price*x.qty;return `<div class="cart-row"><span><b>${x.name}</b><br>₹${x.price} × ${x.qty}</span><button onclick="removeItem(${i})">Remove</button></div>`}).join('');document.getElementById('total').textContent=total}
function removeItem(i){let c=getCart();c.splice(i,1);saveCart(c);renderCart()}
function placeOrder(e){e.preventDefault();if(!getCart().length){alert('Your cart is empty.');location.href='shop.html';return}alert('Thank you! Your demo order has been received. Connect a backend/payment gateway before accepting live orders.');localStorage.removeItem('obbattuCart');location.href='index.html'}
updateCount();
