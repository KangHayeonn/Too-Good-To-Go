import React from "react";
import styled from "@emotion/styled";
import Titlebar from "../../atoms/Titlebar/Titlebar";
import SelectInfo from "../../molecules/Order/SelectInfo";
import OrderInfo from "../../molecules/Order/OrderInfo";

const OrderList: React.FC = () => {
	return (
		<Wrapper>
			<Titlebar>주문내역</Titlebar>
			<SelectInfo />
			<OrderInfo />
		</Wrapper>
	);
};

export default OrderList;

const Wrapper = styled.div``;
