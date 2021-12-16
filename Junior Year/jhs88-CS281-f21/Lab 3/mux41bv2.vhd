-- MULTIPLEXER 4-1 BEHAVIORAL

library IEEE;
use IEEE.std_logic_1164.all;

entity mux41bv2 is
        port (A, B, C, D: in std_logic;
        SEL: in std_logic_vector (1 downto 0);
        Z: out std_logic);
end mux41bv2;

architecture behavioral of mux41bv2 is

begin
  Z <= A when SEL = "00" else
       B when SEL = "01" else
       C when SEL = "10" else
       D;
end behavioral;

