import React from "react";
import styled from "@emotion/styled";
import Titlebar from "../../atoms/Titlebar/Titlebar";
import DiscountInfo from "../../molecules/Order/DiscountInfo";

const DiscountForm: React.FC = () => {
	return (
		<Wrapper>
			<Titlebar color="#D6E3DD">포인트 및 할인쿠폰</Titlebar>
			<DiscountInfo />
		</Wrapper>
	);
};

export default DiscountForm;

const Wrapper = styled.div``;
