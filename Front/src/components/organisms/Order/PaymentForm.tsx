import React from "react";
import styled from "@emotion/styled";
import Titlebar from "../../atoms/Titlebar/Titlebar";
import SelectInfo from "../../molecules/Order/SelectInfo";
import OrderInfo from "../../molecules/Order/OrderInfo";

const PaymentForm: React.FC = () => {
	return (
		<Wrapper>
			<Titlebar>결제수단</Titlebar>
			<SelectInfo />
			<OrderInfo />
		</Wrapper>
	);
};

export default PaymentForm;

const Wrapper = styled.div``;
