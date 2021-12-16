import React from "react";
import styled from "@emotion/styled";
import Titlebar from "../../atoms/Titlebar/Titlebar";
import SelectInfo from "../../molecules/Order/SelectInfo";
import OrderInfo from "../../molecules/Order/OrderInfo";

const PayForm: React.FC = () => {
	return (
		<Wrapper>
			<Titlebar>결제정보</Titlebar>
			<SelectInfo />
			<OrderInfo />
		</Wrapper>
	);
};

export default PayForm;

const Wrapper = styled.div``;
