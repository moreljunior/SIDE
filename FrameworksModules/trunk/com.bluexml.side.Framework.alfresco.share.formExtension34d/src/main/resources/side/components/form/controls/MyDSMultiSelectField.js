/**
 * SIDE root namespace.
 * 
 * @namespace SIDE
 */
// Ensure SIDE root object exists
if (typeof SIDE == "undefined" || !SIDE) {
	var SIDE = {};
}
if (console == undefined) {
	// create a fake console object to avoid error (console is provided by
	// firebug)
	var console = {
		log : function(msg) {
		}
	};
}

(function() {

	/**
	 * Create a multi select field
	 * 
	 * @class SIDE.MyDSMultiSelectField
	 * @extends inputEx.SelectField
	 * @constructor
	 * @param {Object}
	 *            options Added options:
	 *            <ul>
	 *            <li>choices: contains the list of choices configs
	 *            ([{value:'usa'}, {value:'fr', label:'France'}])</li>
	 *            </ul>
	 */
	SIDE.MyDSMultiSelectField = function(options, initialValue) {
		SIDE.MyDSMultiSelectField.superclass.constructor.call(this, options);
		this.initialValue = initialValue;
		this.log("initialValue DSMulti :" + initialValue);
	};

	YAHOO.lang.extend(SIDE.MyDSMultiSelectField, inputEx.DSSelectField, {
		log : function(msg) {
			console.log("[SIDE.MyDSMultiSelectField] " + msg);
		},
		/**
		 * Build the DDList
		 */
		renderComponent : function() {

			SIDE.MyDSMultiSelectField.superclass.renderComponent.call(this);

			this.ddlist = new inputEx.widget.DDList({
				parentEl : this.fieldContainer
			});

		},

		/**
		 * Register the "change" event
		 */
		initEvents : function() {
			YAHOO.util.Event.addListener(this.el, "change", this.onAddNewItem, this, true);
			this.ddlist.itemRemovedEvt.subscribe(this.onItemRemoved, this, true);
			this.ddlist.listReorderedEvt.subscribe(this.fireUpdatedEvt, this, true);
		},

		/**
		 * Re-enable the option element when an item is removed by the user
		 */
		onItemRemoved : function(e, params) {

			this.showChoice({
				value : params[0]
			});
			this.el.selectedIndex = 0;

			this.fireUpdatedEvt();

		},

		/**
		 * Add an item to the list when the select changed
		 */
		onAddNewItem : function() {

			var value, position, choice;

			if (this.el.selectedIndex !== 0) {

				// Get the selector value
				value = SIDE.MyDSMultiSelectField.superclass.getValue.call(this);

				position = this.getChoicePosition({
					value : value
				});
				choice = this.choicesList[position];

				this.ddlist.addItem({
					value : value,
					label : choice.label
				});

				// hide choice that has just been selected (+ select first
				// choice)
				this.hideChoice({
					position : position
				});
				this.el.selectedIndex = 0;

				this.fireUpdatedEvt();

			}
		},

		/**
		 * Set the value of the list
		 * 
		 * @param {String}
		 *            value The value to set
		 * @param {boolean}
		 *            [sendUpdatedEvt] (optional) Wether this setValue should
		 *            fire the updatedEvt or not (default is true, pass false to
		 *            NOT send the event)
		 */
		setValue : function(value, sendUpdatedEvt) {

			var i, length, position, choice, ddlistValue = [];

			if (!YAHOO.lang.isArray(value)) {
				return;
			}

			// Re-show all choices
			for (i = 0, length = this.choicesList.length; i < length; i += 1) {
				this.showChoice({
					position : i
				});
			}

			// Hide selected choices and fill ddlist value
			for (i = 0, length = value.length; i < length; i += 1) {

				position = this.getChoicePosition({
					value : value[i]
				});
				choice = this.choicesList[position];

				ddlistValue.push({
					value : choice.value,
					label : choice.label
				});

				this.hideChoice({
					position : position
				});
			}

			// set ddlist value
			this.ddlist.setValue(ddlistValue);

			// reset select to first choice
			this.el.selectedIndex = 0;

			if (sendUpdatedEvt !== false) {
				// fire update event
				this.fireUpdatedEvt();
			}
		},

		/**
		 * Return the value
		 * 
		 * @return {Any} an array of selected values
		 */
		getValue : function() {
			return this.ddlist.getValue();
		},
		/**
		 * Callback for request success
		 */
		onDatasourceSuccess : function(oRequest, oParsedResponse, oPayload) {
			this.populateSelect(oParsedResponse.results);
			this.addChoice({
				value : '',
				label : '',
				position : 0
			});
			this.log("dataloaded");
			if (this.initialValue) {
				var valuesList = this.initialValue.split(",");
				this.log("dataloaded init old:" + this.getValue());
				this.setValue(valuesList);
				this.log("dataloaded init new:" + this.getValue());
			} else {
				this.setValue([ '' ], false);
			}
		}

	});

	// Register this class as "multiselect" type
	inputEx.registerType("dsmultiselect", SIDE.MyDSMultiSelectField);

}());