-- TEST BENCH 1-BIT ALU WITH SUBTRACTION AND OVERFLOW DETECTION BEHAVIORAL

library IEEE;
use IEEE.std_logic_1164.all;

entity tb_alu1sob is
end entity;

architecture behavioral of tb_alu1sob is

component alu1sob
 port(
  a,b,CarryIn,binvert : in std_logic;
      Operation : in std_logic_vector (1 downto 0);
         Result, CarryOut, Overflow : out std_logic
  );
end component;

  signal myA, myB, myC, myBinv, myRes, myC_out, myOvf : STD_LOGIC := '1';
  signal myOp : std_logic_vector (1 downto 0);

begin

   TB: alu1sob
    port map (
    a => myA,
    b => myB, 
    CarryIn => myC,
    binvert => myBinv,
    Operation => myOp,
    Result => myRes,
    CarryOut => myC_out,
    Overflow => myOvf
    );
   
  myOp <= "10" after   0 ns, "00" after 200 ns;

   process
   begin
   	myA <= not myA;
   	wait for 10 ns;
   end process;

   process
   begin
   	myB <= not myB;
   	wait for 20 ns;
   end process;

   process
   begin
   	myC <= not myC;
   	wait for 40 ns;
   end process;

   process
   begin
   	myBinv <= not myBinv;
   	wait for 80 ns;
   end process;

end behavioral;

