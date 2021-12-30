import React from "react";
import styled from "@emotion/styled";
import { css } from "@emotion/react";
import { Link } from "react-router-dom";
import PageTemplate from "../../components/templates/PageTemplate";
import OrderForm from "../../components/organisms/Order/OrderForm";
import RequestForm from "../../components/organisms/Order/RequestForm";
import OrderList from "../../components/organisms/Order/OrderList";
import PaymentForm from "../../components/organisms/Order/PaymentForm";
import DiscountForm from "../../components/organisms/Order/DiscountForm";
import PayForm from "../../components/organisms/Order/PayForm";

const Button = styled.div`
	width: 100%;
	display: flex;
	justify-content: center;
	margin-top: 3em;
	margin-bottom: 7em;
`;

const OrderButton = styled.button`
	width: 21%;
	background: #387358;
	margin-top: 3em;
	padding: 0.7em;
	display: flex;
	flex-direction: column;
	align-items: center;
	font-size: 19px;
	font-weight: 600;
	box-shadow: 0px 1px 6px rgba(0, 0, 0, 0.1);
	border-radius: 10px;

	&:hover {
		background-color: #a4a4a4;
	}
`;

const pay = css`
	color: #fff;
`;

const OrderPage: React.FC = () => {
	return (
		<PageTemplate isHeader={false} isSection={false} isFooter={false}>
			<OrderForm />
			<RequestForm />
			<OrderList />
			<PaymentForm />
			<DiscountForm />
			<PayForm />
			<Button>
				<OrderButton type="button">
					<Link to="/pay" css={pay}>
						주문하기
					</Link>
				</OrderButton>
			</Button>
		</PageTemplate>
	);
};

export default OrderPage;
