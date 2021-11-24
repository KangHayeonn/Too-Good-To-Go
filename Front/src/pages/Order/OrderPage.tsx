import React from "react";
import PageTemplate from "../../components/templates/PageTemplate";
import OrderForm from "../../components/organisms/Order/OrderForm";
import RequestForm from "../../components/organisms/Order/RequestForm";

const OrderPage: React.FC = () => {
	return (
		<PageTemplate
			isHeader={false}
			isSection={false}
			isFooter={false}
		>
			<OrderForm />
            <RequestForm />
		</PageTemplate>
	);
};

export default OrderPage;
