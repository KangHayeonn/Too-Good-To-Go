import React from "react";
import styled from "@emotion/styled";
import Titlebar from "../../atoms/Titlebar/Titlebar";
import OrderListInfo from "../../molecules/Order/OrderList";

const OrderList: React.FC = () => {
	return (
		<Wrapper>
			<Titlebar color="#D6E3DD">주문내역</Titlebar>
			<OrderListInfo />
		</Wrapper>
	);
};

export default OrderList;

const Wrapper = styled.div``;
