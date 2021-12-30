import React, {FC, useState, useCallback, useRef, useEffect} from 'react';
import styled from '@emotion/styled';

const Container = styled.div`
    background: rgba(0, 0, 0, 0.5);
    position:fixed;
    top:0;
    left:0;
    right:0;
    bottom: 0;
    display:flex;
    justify-content:center;
    align-items: center;
    padding:15px;
`;
const Popup = styled.div`
    width: 100%;
    max-width:400px;
    border-radius:10px;
    overflow:hidden;
    background: #6EC19B;
    box-shadow: 5px 10px 10px 1px rgba(0,0,0, 0.3);
`;
const Header = styled.div`
    width: 100%;
    height:50px;
    display:flex;
    align-items:center;
    justify-content: center;
    font-size: 19px;
    font-weight: bold;
    color: black;
`;
const Body = styled.div`
    width: 100%;
    background : #ffffff;
    display:flex;
    justify-content: center;
    padding-top: 15px;
    padding-bottom: 15px;
`;
const Footer = styled.div`
    width: 100%;
    display: inline-flex;
    height: 40px;
    justify-content: center;
    align-tems: center;
    float : left;
    color: #ffffff;
    cursor: pointer;

    .pop-btn {
        width: 50%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .pop-btn.confirm {
        border-right:1px solid #D6E3DD; //오른쪽 줄
    }
`;
const MenuBox = styled.ul`
    width : 100%;
    height: 100%;
    min-height:100px;         //최소 높이
    max-height:200px;         //최대 높이
`;
interface Props {
    show: boolean;
}
const PaymentModal: React.FC<Props> = ({show}) => {
    // show가 false면 화면에 메뉴를 나타내지 않음
    if(!show) {
        return null;
    }
    // show가 true면 아래 메뉴가 화면에 나타남
    return (
        <Container> 
            <Popup>
                <Header>
                    <span className="head-title">결제수단선택</span>
                </Header>
                <Body>
                    <div className="body-contentbox">
                        <MenuBox>
                            <li>무통장 입금</li>
                            <li>신용/체크카드</li>
                            <li>핸드폰 결제</li>
                            <li>카카오페이</li>
                            <li>직접 만나서 결제</li>
                        </MenuBox>
                    </div>
                </Body>
                <Footer> 
                    <span className="pop-btn confirm" id="confirm">확인</span>
                    <span className="pop-btn close" id="close">창 닫기</span>
                </Footer>
            </Popup>
        </Container>
    );
};

export default PaymentModal;