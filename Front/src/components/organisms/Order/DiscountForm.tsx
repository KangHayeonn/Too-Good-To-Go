import React from "react";
import styled from "@emotion/styled";
import Titlebar from "../../atoms/Titlebar/Titlebar";
import SelectInfo from "../../molecules/Order/SelectInfo";
import OrderInfo from "../../molecules/Order/OrderInfo";

const DiscountForm: React.FC = () => {
	return (
		<Wrapper>
			<Titlebar>포인트 및 할인쿠폰</Titlebar>
			<SelectInfo />
			<OrderInfo />
		</Wrapper>
	);
};

export default DiscountForm;

const Wrapper = styled.div``;
