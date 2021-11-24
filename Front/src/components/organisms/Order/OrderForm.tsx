import React from "react";
import styled from "@emotion/styled";
import Titlebar from "../../atoms/Titlebar/Titlebar";
import SelectInfo from "../../molecules/Order/SelectInfo";
import OrderInfo from "../../molecules/Order/OrderInfo";

const OrderForm: React.FC = () => {
    return (
        <Wrapper>
            <Titlebar>주문정보</Titlebar>
            <SelectInfo/>
            <OrderInfo/>
        </Wrapper>
    );
};

export default OrderForm;

const Wrapper = styled.div`

`;