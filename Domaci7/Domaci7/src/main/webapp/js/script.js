let tabela = document.getElementById('coupons-tbl').tBodies[0];

$( document ).ready(function() {
	$.get( "rest/coupons/shops", function( data ) {
		for (d of data) {
			  $("#shop-list").append(new Option(d.name, d.id));
		  }
		});

	fill();
	
	$("#testBtn").click(function() {
		$.ajax({
			type: "PUT",
			url: 'rest/coupons',
			success: function(data) {
				addEntry(data);
			}
		})
	});
	
	
	var form = document.getElementById('add-coupon-form');
	if (form.attachEvent) {
	    // IE support
	    form.attachEvent("submit", processForm);
	} else {
	    form.addEventListener("submit", processForm);
	}
	
});


function createCoupon(shop, product, discountedPrice, originalPrice) {
	$.post("rest/coupons", $( "#add-coupon-form" ).serialize(), function(data) {
		console.log(data);
		addEntry(data);
		$("#add-coupon-form")[0].reset();
	});
}


function processForm(e) {
	if (e.preventDefault) e.preventDefault();
//    var formData = new FormData(e.target);

    createCoupon();
    
    return false;
}


function fill() {
	$.get( "rest/coupons", function( data ) {
		
		for (d of data) {	
			addEntry(d);
		}
	});
}


function addEntry(data) {
	let tr = document.createElement('tr');
	
	let id = data.id;
	
	let info = [data.product, data.shopName, data.discountedPrice, data.originalPrice, Math.round(data.sale)+"%"];
	for (i of info) {
		let td = document.createElement('td');
		td.appendChild(document.createTextNode(i));
		tr.appendChild(td);
	}
	
	tabela.appendChild(tr);
	
	let btnDelete = document.createElement('button');
	btnDelete.setAttribute('type', 'button');
	btnDelete.innerText = 'Delete';
	btnDelete.addEventListener('click', e => {
		$.ajax({
            type: "DELETE",
            url: 'rest/coupons/' + id,
            success: function (data2) {
                
                if (data2 == "true") {
                	tabela.removeChild(tr);
                }
            },
            error: function (data2) {
                console.log('Error:', data2);
            }
        });

	});
	
	let tdbt = document.createElement('td');
	tdbt.appendChild(btnDelete);
	tr.appendChild(tdbt);
}