jQuery.extend(jQuery.validator.messages, {
		required: "This field is required.",
		remote: "This name is existed.",
		email: "Please enter a valid email address.",
		url: "Please enter a valid URL.",
		date: "Please enter a valid date.",
		dateISO: "Please enter a valid date (ISO).",
		number: "Please enter a valid number.",
		digits: "Please enter a valid digit.",
		digitss: "Please enter integer.",
		digitsCustom: "Please enter integer.",
		creditcard: "Please enter a valid credit card number.",
		equalTo: "Please enter the same value again.",
		equalToLimit: "Please enter the value that be more than or equal to both of the values above.",
		accept: "Please enter a value with a valid extension.",
		maxlength: $.validator.format("Please enter no more than {0} characters."),
		minlength: $.validator.format("Please enter at least {0} characters."),
		len: $.validator.format("Please enter a length of the string is {0}"),
		rangelength: $.validator.format("Please enter a value between {0} and {1} characters long."),
		range: $.validator.format("Please enter a value between {0} and {1}."),
		rangeLimit: $.validator.format("Please enter a value less than {1} and {2} is an integer multiple of the value."),
		max: $.validator.format("Please enter a value less than or equal to {0}."),
		min: $.validator.format("Please enter a value greater than or equal to {0}."),
		mac: "Please enter illegal mac address, mac format like 00-24-21-19-bD-E4",
		macAddr:"Support fuzzy query MAC address, MAC address format:“00-1A-16-”",
		ip: "Please enter illegal ip address, ip format like 192.168.22.254",
		subnetworkMask:"Please enter illegal subnetowrk mask, subnetwork mask format like 255.255.255.0",
		subnetworkMaskRange:"Subnetwork range must be greater than ip range",
		sameNetworkSegment:"{2} and {3} must be in same network segment",
		unicastAddress:"Please enter unicast ip address",
		ipSearch:"Support fuzzy query IP address, IP address format：“192.168.0.1”",
		anyString:"Please enter any string.",
		moreThan:"End VLAN Must be greater than Starting VLAN",
		moreThanTerminalCode:"End Code Must be greater than Starting Code",
		moreThanTime:"End Date Must be greater than Start Date",
		moreThanIp:"End IP Must be greater than Start IP",
		moreThanIpRange:"End IP Must be greater than Start IP. IP range must be no more than {0}",
		notMoreThan200:"End VLAN should not be more than 200 than Starting VLAN",
		lessThan:"Starting VLAN Must be less than End VLAN",
		filtSpecialChar:"Not allowed to enter ~ ` ! @ # $ % ^ & * ( ) + = { } [ ] \" \' \< \> ? | \\",
		space:"Not allowed to enter ~ ` ! @ # $ % ^ & * ( ) + = { } [ ] \" \' \< \> ? | \\ and space",
		legalChar:"Please enter a string only include digit,character,Chinese characters ",
		phone:"Please enter a valid phone number.",
		mobile:"Please enter a valid mobile number.",
		tel:"Please enter a valid telephone number.",
		userName:"Please enter a string only include digit, character, Chinese characters, and underscores.",
		fastLeaveAbility:"4 figures, 1 indicates support, 0 did not support.",
		ipRange:"Please enter a valid IP address.IP range: 224.0.1.0 ~ 238.255.255.255",
		isMiddle:"ip must between startIp and endIp",
		vlanInputOtherRule:"Range of VLAN if from 1 to 4094. VLAN is not allowed repeated. Count of VLAN should not be more than {0}！",
		vlanTransInput:"Please enter illegal value , such as：3-4,6-5。vlan range is 1-4094，original vlan not allowed repeated，It must be less than 9 groups！",
		valueRange:"Please enter illegal value from {0} to {1}。such as：3-4。",
		vlanRange:"Please enter illegal value from {0} to {1}.You are allowed to input a number or a range of Number(Such as beginVlan-endVlan. The endVlan must be greater than beginVlan).",
		vlanAssociateRule:"The match range only be allowed to use one time.Please input a number!",
		mixModeMacLoid:"Only MAC or LOID is required.",
		leastMacOrLoid:"Fill in at least one item between MAC and LOID.",
	    mixModePwd:"Password is not requiped when MAC is filled",
	    noRepeatMac:"MAC not allowed repeated",
	    noRepeatLoid:"LOID not allowed repeated",
	    noRepeatLoidAndPassword:"LOID+PASSWORD not allowed repeated",
	    noRepeat:"not allowed repeated",
	    customTypes:"Please enter 2 byte hexadecimal characters",
	    vlanRule:"Please enter the value,from{0} to {1},such as:3,6,7,not allowed repeated,Count of VLAN should not be more than {2}",
	    lessEqualPledgeBandwidth:"Fixed bandWidth must be no more than pledge bandwidth",
	    notIgmpIp:"Multicast ip is not allowed.",
	    noRequiredPmParam:"No available collection parameter with selected conditions.Please select again.",
	    noLessThanGeneral:"Max value is no less than min value.",
	    macCharValidate:"Only allowed to input 0-9 a-f A-F -",
	    ipCharValidate:"Only allowed to input . 0-9",
	    notEqual:"{1} is not allowed to equal to {2}！"
});