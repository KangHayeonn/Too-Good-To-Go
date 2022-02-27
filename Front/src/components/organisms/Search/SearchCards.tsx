import React from 'react';
import styled from "@emotion/styled";

// 할인율 계산
const calculatedDiscount = (price: number, discountedPrice: number): number => {
	return Math.ceil((1 - discountedPrice / price) * 100);
};

const SearchCards = () => {
    return (
        <Wrapper>
            <SearchCard>
                <div className="card-img-ctn">
                    <img src="http://thingool.hgodo.com/gd5replace/thingotr4652/data/editor/goods/210617/285f14f889d5eb44a9e691f9f0ac985f_174805.jpg" alt="Food" />
                </div>
                <div className="cardInfo">
                    <ShopTitle>
                        <p className="cardInfo-shopName">
                            강여사 김치찜
                        </p>
                        <p className="cardInfo-category">
                            한식
                        </p>
                    </ShopTitle>
                    <p className="cardInfo-shopFoodName">
                        등갈비 김치찜
                    </p>
                    <div className="cardInfo-price">
                        <p>
                            {calculatedDiscount(
                                10000,
                                9000
                            )}
                            %
                        </p>
                        <p>9000원</p>
                        <s>(10000원)</s>
                    </div>
                </div>
            </SearchCard>
            <Line />
            <SearchCard>
                <div className="card-img-ctn">
                    <img src="https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20211005_84%2F1633427100547JYF19_PNG%2F1u1r0WjPhZou6UMzRjVqb6us.png&type=a340" alt="Food" />
                </div>
                <div className="cardInfo">
                    <ShopTitle>
                        <p className="cardInfo-shopName">
                            맛있는 치킨집
                        </p>
                        <p className="cardInfo-category">
                            치킨, 패스트푸드
                        </p>
                    </ShopTitle>
                    <p className="cardInfo-shopFoodName">
                        후라이드치킨
                    </p>
                    <div className="cardInfo-price">
                        <p>
                            {calculatedDiscount(
                                10000,
                                9000
                            )}
                            %
                        </p>
                        <p>9000원</p>
                        <s>(10000원)</s>
                    </div>
                </div>
            </SearchCard>
            <Line />
            <SearchCard>
                <div className="card-img-ctn">
                    <img src="https://www.hsd.co.kr/assets/images/main/main_img_01.jpg" alt="Food" />
                </div>
                <div className="cardInfo">
                    <ShopTitle>
                        <p className="cardInfo-shopName">
                            치즈퓨전한식 맛집
                        </p>
                        <p className="cardInfo-category">
                            한식
                        </p>
                    </ShopTitle>
                    <p className="cardInfo-shopFoodName">
                        치즈불닭볶음밥
                    </p>
                    <div className="cardInfo-price">
                        <p>
                            {calculatedDiscount(
                                10000,
                                9000
                            )}
                            %
                        </p>
                        <p>9000원</p>
                        <s>(10000원)</s>
                    </div>
                </div>
            </SearchCard>
            <Line />
        </Wrapper>
    );
};

const Wrapper = styled.div`
	width: 820px;
	height: 730px;
	overflow: visible;
	overflow-x: hidden;
	padding-left: 10px;
    padding-right: 10px;
	margin-right: 25px;
	::-webkit-scrollbar {
		width: 10px;
	}
	::-webkit-scrollbar-thumb {
		background-color: #e7e4e4;
		border-radius: 12px;
		background-clip: padding-box;
		border: 2px solid transparent;
	}
	::-webkit-scrollbar-track {
		background-color: white;
		border-radius: 10px;
		box-shadow: inset 0px 0px 5px white;
	}
    margin-left : 11.5em;
    margin-top : 2em;
`;

const SearchCard = styled.div`
    border: 1px solid #eee;
	width: 805px;
	height: 181px;
	padding: 10px 10px 10px 0;
	margin: 0 25px 20px 0;
	display: flex;
	align-items: center;
	border-radius: 8px;

	transition: 0.3s;

	.card-img-ctn {
		width: 134px;
		margin-right: 9px;
		margin-left: 10px;
	}

	img {
		width: 161px;
		height: 159px;
		overflow: hidden;
		margin-left: 14px;
		border-radius: 3px;
	}

	.cardInfo {
		display: flex;
		align-items: flex-start;
		flex-direction: column;
		padding-left: 50px;
		width: 550px;
	}

	.cardInfo > * {
		margin: 2px;
		margin-left: 14px;
	}

	.cardInfo-shopName {
        font-size: 22px;
		font-weight: 700;
        margin-bottom: 13px;
        padding-right: 11px;
        border-right : 3px solid #E2E2E2;
	}

    .cardInfo-category {
        font-size: 22px;
        margin-bottom: 13px;
        margin-left: 11px;
        color: #58C656;
        font-weihgt: 400;
    }

	.cardInfo-shopFoodName {
		font-size: 18px;
	}

	.cardInfo-price {
		display: flex;
		font-size: 17px;
		font-weight: 500;
	}

	.cardInfo-price > p:first-of-type {
		color: #2f8d09;
		padding-right: 5px;
		font-weight: 700;
	}
`;

const ShopTitle = styled.div`
    display : flex;
`

const Line = styled.div`
    margin-top: 1.5em;
    margin-bottom: 1.5em;
    border-bottom: 3px solid #eee;
`

export default SearchCards;