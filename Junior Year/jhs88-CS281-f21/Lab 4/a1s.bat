  del *.cf *.vcd
  ghdl -a    and_2.vhd
  ghdl -a     or_2.vhd
  ghdl -a    mux41b.vhd
  ghdl -a    half_adder_behavioral.vhd
  ghdl -a    full_adders.vhd
  ghdl -a    alu1_structural.vhd
  ghdl -a tb_alu1_structural.vhd
:
  ghdl -e    and_2
  ghdl -e     or_2
  ghdl -e    mux41
  ghdl -e    half_adder
  ghdl -e    full_adder
  ghdl -e    alu1s
  ghdl -e tb_alu1s
  ghdl -r tb_alu1s --stop-time=200ns --disp-time --vcd=tb_alu1s.vcd
  gtkwave tb_alu1s.vcd
